package com.example.demo.listener;

import com.example.common.kafka.event.PaymentCompletedEvent;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Payment başarılı olduğunda tetiklenir ve statüyü CREATED'den PAID'e alır.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedEventListener {
    private final OrderService orderService;

    @KafkaListener(topics = "#{@kafkaProperties.topics.paymentCompleted.name}",
            groupId = "#{@kafkaProperties.topics.paymentCompleted.groupId}",
            concurrency = "#{@kafkaProperties.topics.paymentCompleted.concurrency}")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("PaymentCompletedEvent received for orderId={}, transitioning order to PAID", event.orderId());
        orderService.setOrderToPaid(event.orderId());
    }
}
