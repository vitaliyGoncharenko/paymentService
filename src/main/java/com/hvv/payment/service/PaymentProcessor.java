package com.hvv.payment.service;

import com.hvv.payment.repository.model.Payment;

public interface PaymentProcessor {
    void processPaymentAsync(Payment payment);
}
