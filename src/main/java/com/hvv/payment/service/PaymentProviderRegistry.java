package com.hvv.payment.service;

import com.hvv.payment.model.Provider;

public interface PaymentProviderRegistry {
    PaymentProvider getPaymentProvider(Provider provider);
}
