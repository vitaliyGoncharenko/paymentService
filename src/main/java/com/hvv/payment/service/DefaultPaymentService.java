package com.hvv.payment.service;

import com.hvv.payment.exceptions.CustomConcurrencyException;
import com.hvv.payment.repository.PaymentRepository;
import com.hvv.payment.repository.model.Payment;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;

    //TODO move variables to the properties
    @Retryable(
            retryFor = {CustomConcurrencyException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 500, multiplier = 2)
    )
    public Payment updatePayment(Payment payment) {
        try {
            return paymentRepository.save(payment);
        } catch (OptimisticLockException e) {
            throw new CustomConcurrencyException("Payment was updated by another process. Please retry.", e);
        }
    }

    public Payment findByRequestId(String requestId) {
            return paymentRepository.findByRequestId(requestId);
    }

    @Recover
    public void recoverPaymentUpdate(Exception e, Payment payment) {
        if (e instanceof CustomConcurrencyException) {
            //TODO Handle concurrency-specific recovery
            log.warn("Concurrency issue occurred while updating payment: {}", payment.getId(), e);
        }
        throw new RuntimeException("Failed to update payment after multiple attempts.", e);
    }
}