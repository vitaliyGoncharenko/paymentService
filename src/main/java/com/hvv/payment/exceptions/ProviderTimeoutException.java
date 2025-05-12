package com.hvv.payment.exceptions;

public class ProviderTimeoutException extends RuntimeException {
    public ProviderTimeoutException(String message) {
        super(message);
    }
}