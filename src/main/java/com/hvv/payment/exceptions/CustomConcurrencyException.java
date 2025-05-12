package com.hvv.payment.exceptions;

public class CustomConcurrencyException extends RuntimeException {

    public CustomConcurrencyException(String message) {
        super(message);
    }

    public CustomConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}