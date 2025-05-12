package com.hvv.payment.service;

import com.hvv.payment.model.PaymentContext;
import com.hvv.payment.model.PaymentResponse;
import com.hvv.payment.model.Provider;
import com.hvv.payment.repository.model.Payment;
import com.hvv.payment.repository.model.PaymentStatus;
import com.hvv.payment.service.metrics.MetricsService;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultPaymentProcessor implements PaymentProcessor {

    private final PaymentService paymentService;
    private final PaymentRouterService paymentRouterService;
    private final RecoveryStrategy recoveryStrategy;
    private final PaymentProviderRegistry paymentProviderRegistry;
    private final MetricsService metricsService;


    @Async
    public void processPaymentAsync(Payment payment) {
        Timer.Sample timerSample = metricsService.startPaymentProcessingTimer();
        log.info("Async payment processing is started for - {}", payment.getUuid());
        try {
            PaymentContext paymentContext = PaymentContext.builder()
                    .amount(payment.getAmount())
                    .currency(payment.getCurrency())
                    .paymentMethod(payment.getPaymentMethod())
                    .build();

            List<Provider> providers = paymentRouterService.route(paymentContext);

            for (Provider provider : providers) {
                try {
                    PaymentProvider paymentProvider = paymentProviderRegistry.getPaymentProvider(provider);
                    PaymentResponse response = paymentProvider.processPayment(payment);

                    if (response.isSuccessful()) {
                        payment.setStatus(PaymentStatus.SUCCESS);
                        paymentService.updatePayment(payment);

                        metricsService.incrementSuccessfulPayments();

                        //TODO notify client via webhook
                        log.info("Payment succeeded with provider: {}", provider);
                        metricsService.stopPaymentProcessingTimer(timerSample);
                        return;
                    }
                } catch (Exception e) {
                    log.warn("Provider {} failed: {}", provider, e.getMessage());
                }
            }

            handleRecovery(payment, timerSample);

        } catch (Exception e) {
            log.error("Payment processing failed: {}", e.getMessage());
            handleRecovery(payment, timerSample);
        }
    }

    private void handleRecovery(Payment payment, Timer.Sample timerSample) {
        payment.setStatus(PaymentStatus.FAILED);
        paymentService.updatePayment(payment);

        recoveryStrategy.recovery(payment);

        metricsService.incrementFailedPayments();
        metricsService.stopPaymentProcessingTimer(timerSample);
    }

}
