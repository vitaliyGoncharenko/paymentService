package com.hvv.payment.service;

import com.hvv.payment.model.PaymentRequest;
import com.hvv.payment.repository.model.Payment;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.hvv.payment.repository.model.PaymentStatus.PENDING;

@Service
@RequiredArgsConstructor
public class DefaultInitiatePaymentService implements InitiatePaymentService {

    private static final Logger logger = LoggerFactory.getLogger(InitiatePaymentService.class);

    private final PaymentService paymentService;;
    private final PaymentProcessor paymentProcessor;

    public String initiatePayment(PaymentRequest request) {
        Payment existingPayment = paymentService.findByRequestId(request.getRequestId());
        if (existingPayment != null) {
            logger.info("Duplicate payment request detected. Returning existing payment: {}", existingPayment.getUuid());
            return existingPayment.getUuid();
        }

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .requestId(request.getRequestId())
                .paymentMethod(request.getPaymentMethod())
                .webhookUrl(request.getWebhookUrl())
                .status(PENDING)
                .build();

        payment = paymentService.updatePayment(payment);

        paymentProcessor.processPaymentAsync(payment);

        logger.info("Payment initiated successfully with payment: {}", payment.getUuid());
        return payment.getUuid();

    }
}
