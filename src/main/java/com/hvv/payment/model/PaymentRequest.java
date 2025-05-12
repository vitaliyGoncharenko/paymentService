package com.hvv.payment.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PaymentRequest{

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    String currency;

    @NotNull(message = "Payment method is required")
    PaymentMethod paymentMethod;

    @NotBlank(message = "Request ID is required")
    String requestId;

    @Pattern(
            regexp = "https://.*",
            message = "Webhook URL must be a valid HTTPS URL. Is using for "
    )
    String webhookUrl;
}
