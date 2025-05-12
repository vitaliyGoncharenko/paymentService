package com.hvv.payment.service;

import com.hvv.payment.model.PaymentRequest;

public interface InitiatePaymentService {
    String initiatePayment(PaymentRequest request);
}