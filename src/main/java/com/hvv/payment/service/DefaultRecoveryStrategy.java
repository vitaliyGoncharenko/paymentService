package com.hvv.payment.service;

import com.hvv.payment.repository.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class DefaultRecoveryStrategy implements RecoveryStrategy{
    @Override
    public void recovery(Payment payment) {
        // TODO send event to the kafka topic
    }
}
