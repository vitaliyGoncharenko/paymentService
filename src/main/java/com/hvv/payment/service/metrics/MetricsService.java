package com.hvv.payment.service.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final MeterRegistry meterRegistry;
    private final Counter successfulPaymentsCounter;
    private final Counter failedPaymentsCounter;
    private final Timer paymentProcessingTimer;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.successfulPaymentsCounter = meterRegistry.counter("payment.success.count", "type", "payment");
        this.failedPaymentsCounter = meterRegistry.counter("payment.failure.count", "type", "payment");
        this.paymentProcessingTimer = meterRegistry.timer("payment.processing.time", "type", "payment");
    }

    public Timer.Sample startPaymentProcessingTimer() {
        return Timer.start(meterRegistry);
    }

    public void stopPaymentProcessingTimer(Timer.Sample sample) {
        sample.stop(paymentProcessingTimer);
    }

    public void incrementSuccessfulPayments() {
        successfulPaymentsCounter.increment();
    }

    public void incrementFailedPayments() {
        failedPaymentsCounter.increment();
    }
}
