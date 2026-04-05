package com.example.common.kafka.event;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Payment Service → Restaurant Service
 * Ödeme başarılı olduğunda yayımlanır.
 */
public record PaymentCompletedEvent(
        Long orderId,
        UUID restaurantId,
        BigDecimal amount,
        java.time.Instant orderTime   // sipariş saati — restaurant açık mı kontrolü için
) implements Event {
}
