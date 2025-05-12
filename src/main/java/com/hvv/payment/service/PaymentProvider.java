package com.hvv.payment.service;

import com.hvv.payment.model.PaymentResponse;
import com.hvv.payment.model.Provider;
import com.hvv.payment.repository.model.Payment;

public interface PaymentProvider {
    Provider getProviderName();

    PaymentResponse processPayment(Payment payment);

}
