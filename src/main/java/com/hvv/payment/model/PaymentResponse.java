package com.hvv.payment.model;

public record PaymentResponse(String paymentId, String status, boolean isSuccessful) {
}