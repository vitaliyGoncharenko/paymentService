package com.hvv.payment.service;

import com.hvv.payment.model.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DefaultPaymentProviderRegistry implements PaymentProviderRegistry{
    private final Map<Provider, PaymentProvider> providerMap;

    @Autowired
    public DefaultPaymentProviderRegistry(List<PaymentProvider> providers) {
        providerMap = providers.stream()
                .collect(Collectors.toMap(PaymentProvider::getProviderName, provider -> provider));
    }

    public PaymentProvider getPaymentProvider(Provider provider) {
        return Optional.ofNullable(providerMap.get(provider))
                .orElseThrow(() -> {
                    String errorMessage = String.format("No PaymentProvider found for provider: %s", provider);
                    log.error(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }
}
