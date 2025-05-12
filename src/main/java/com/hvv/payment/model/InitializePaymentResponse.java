package com.hvv.payment.model;

import com.hvv.payment.repository.model.PaymentStatus;

public record InitializePaymentResponse(String paymentId, PaymentStatus status) {
}