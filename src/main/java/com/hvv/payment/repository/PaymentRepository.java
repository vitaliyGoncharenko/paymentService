package com.hvv.payment.repository;

import com.hvv.payment.repository.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByRequestId(String requestId);

}
