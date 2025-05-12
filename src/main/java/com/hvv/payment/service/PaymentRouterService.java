package com.hvv.payment.service;

import com.hvv.payment.model.PaymentContext;
import com.hvv.payment.model.Provider;

import java.util.List;

public interface PaymentRouterService {
    List<Provider> route(PaymentContext context);
}
