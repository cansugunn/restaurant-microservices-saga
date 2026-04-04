package com.example.common.kafka.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(UUID customerId,
                                Long orderId,
                                BigDecimal amount) implements Event {
}
