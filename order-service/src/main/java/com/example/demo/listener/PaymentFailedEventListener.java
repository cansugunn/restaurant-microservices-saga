package com.example.demo.listener;

import com.example.common.kafka.event.PaymentFailedEvent;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Payment bakiye kontrolü başarısız olduğunda tetiklenir.
 * Order'ı CANCELLED statüsüne alır (compensating transaction).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailedEventListener {
    private final OrderService orderService;

    @KafkaListener(topics = "#{@kafkaProperties.topics.paymentFailed.name}",
            groupId = "#{@kafkaProperties.topics.paymentFailed.groupId}",
            concurrency = "#{@kafkaProperties.topics.paymentFailed.concurrency}")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.warn("PaymentFailedEvent received for orderId={}. Reason: {}", event.orderId(), event.failureReason());
        orderService.cancelOrder(event.orderId(), event.failureReason());
    }
}
