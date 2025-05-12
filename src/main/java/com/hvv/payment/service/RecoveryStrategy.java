package com.hvv.payment.service;

import com.hvv.payment.repository.model.Payment;

public interface RecoveryStrategy {
    void recovery(Payment payment);
}
