package com.example.demo.listener;

import com.example.common.kafka.event.RestaurantApprovedEvent;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Restaurant siparişi onayladığında tetiklenir (açık saatler içinde).
 * Order'ı SUCCESS statüsüne alır.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantApprovedEventListener {
    private final OrderService orderService;

    @KafkaListener(topics = "#{@kafkaProperties.topics.restaurantApproved.name}",
            groupId = "#{@kafkaProperties.topics.restaurantApproved.groupId}",
            concurrency = "#{@kafkaProperties.topics.restaurantApproved.concurrency}")
    public void handleRestaurantApproved(RestaurantApprovedEvent event) {
        log.info("RestaurantApprovedEvent received for orderId={}", event.orderId());
        orderService.approveOrder(event.orderId());
    }
}
