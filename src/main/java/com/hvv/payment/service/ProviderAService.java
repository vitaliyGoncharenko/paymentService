package com.hvv.payment.service;

import com.hvv.payment.exceptions.ProviderTimeoutException;
import com.hvv.payment.exceptions.TooManyRequestsException;
import com.hvv.payment.model.PaymentResponse;
import com.hvv.payment.model.Provider;
import com.hvv.payment.repository.model.Payment;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class ProviderAService implements PaymentProvider {

    private final Random random = new Random();

    @Override
    public Provider getProviderName() {
        return Provider.PROVIDER_A;
    }

    @Retryable(
            retryFor = {TooManyRequestsException.class, ProviderTimeoutException.class},
            maxAttemptsExpression = "${retry.providerA.maxAttempts}",
            backoff = @Backoff(
                    delayExpression = "${retry.providerA.delay}",
                    multiplierExpression = "${retry.providerA.multiplier}",
                    maxDelayExpression = "${retry.providerA.maxDelay}"
            )
    )
    @CircuitBreaker(
            name = "providerA",
            fallbackMethod = "fallback"
    )
    @Override
    public PaymentResponse processPayment(Payment payment) {
        log.info("try to process the payment {} using provider {}", payment.getUuid(), getProviderName());
        int outcome = random.nextInt(2);
        switch (outcome) {
            case 0 -> {
                log.warn("Simulated 429 Too Many Requests");
                throw new TooManyRequestsException("Provider A rate limited (429)");
            }
            case 1 -> {
                log.warn("Simulated timeout");
                throw new ProviderTimeoutException("Provider A timeout");
            }
            default -> throw new IllegalStateException("Unexpected outcome");
        }
    }

    public PaymentResponse fallback(Payment payment, Throwable t) {
        log.error("Circuit breaker fallback for provider: {}, [{}]", t.getMessage(), getProviderName());
        return new PaymentResponse(payment.getUuid(), "Provider A is currently unavailable", false);
    }
}
