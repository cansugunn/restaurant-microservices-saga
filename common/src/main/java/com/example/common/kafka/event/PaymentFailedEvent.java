package com.example.common.kafka.event;

/**
 * Payment Service → Order Service
 * Bakiye yetersiz olduğunda yayımlanır — Order'ı CANCELLED yapar.
 */
public record PaymentFailedEvent(
        Long orderId,
        String failureReason
) implements Event {
}
