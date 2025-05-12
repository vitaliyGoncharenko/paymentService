package com.hvv.payment.controller;

import com.hvv.payment.model.InitializePaymentResponse;
import com.hvv.payment.model.PaymentRequest;
import com.hvv.payment.repository.model.PaymentStatus;
import com.hvv.payment.service.InitiatePaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final InitiatePaymentService initiatePaymentService;

    @PostMapping
    public ResponseEntity<InitializePaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        InitializePaymentResponse response = new InitializePaymentResponse(initiatePaymentService.initiatePayment(request), PaymentStatus.PENDING);
        return ResponseEntity.ok(response);
    }
}