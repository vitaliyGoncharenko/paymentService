package com.hvv.payment.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentContext {
    private BigDecimal amount;
    private String currency;
    private PaymentMethod paymentMethod;
}
