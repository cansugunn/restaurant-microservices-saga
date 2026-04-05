package com.example.demo.listener;

import com.example.common.kafka.event.OrderCreatedEvent;
import com.example.demo.dto.request.CreatePaymentRequestDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventListener {
    private final PaymentService paymentService;
    private final AccountRepository accountRepository;

    @KafkaListener(topics = "#{@kafkaProperties.topics.orderCreated.name}",
            groupId = "#{@kafkaProperties.topics.orderCreated.groupId}",
            concurrency = "#{@kafkaProperties.topics.orderCreated.concurrency}")
    public void processOrderPayment(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent for orderId={}, customerId={}", event.orderId(), event.customerId());

        Account account = accountRepository.findByCustomerId(event.customerId()).orElseThrow(
                () -> new RuntimeException("Account not found for customerId=" + event.customerId()));

        paymentService.createPayment(
                new CreatePaymentRequestDTO(event.orderId(), event.amount(), account.getId()),
                event.restaurantId(),
                event.orderTime()
        );
    }
}
