package com.example.common.kafka.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID customerId,
        Long orderId,
        BigDecimal amount,
        UUID restaurantId,   // Payment → Restaurant için iletilecek
        Instant orderTime    // Restaurant açık mı kontrolü için
) implements Event {
}
