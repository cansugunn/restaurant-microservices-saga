package com.example.common.kafka.event;

/**
 * Restaurant Service → Order Service
 * Restaurant açık ve siparişi onayladığında yayımlanır — Order'ı SUCCESS yapar.
 */
public record RestaurantApprovedEvent(
        Long orderId
) implements Event {
}
