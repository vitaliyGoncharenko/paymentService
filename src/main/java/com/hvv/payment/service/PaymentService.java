package com.hvv.payment.service;

import com.hvv.payment.repository.model.Payment;

public interface PaymentService {
    Payment updatePayment(Payment payment);
    Payment findByRequestId(String requestId);
}