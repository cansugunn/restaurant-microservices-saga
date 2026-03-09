package com.example.demo.listener;

import com.example.common.kafka.event.OrderCreatedEvent;
import com.example.demo.dto.request.CreatePaymentRequestDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedEventListener {
    private final PaymentService paymentService;
    private final AccountRepository accountRepository;

    //todo düzelcek
    @KafkaListener(topics = "#{@kafkaProperties.topics.orderCreated.name}",
            groupId = "#{@kafkaProperties.topics.orderCreated.groupId}",
            concurrency = "#{@kafkaProperties.topics.orderCreated.concurrency}")
    public void processOrderPayment(OrderCreatedEvent event){
        Account account = accountRepository.findByCustomerId(event.customerId()).orElseThrow();
        paymentService.createPayment(new CreatePaymentRequestDTO(
                event.orderId(),
                event.amount(),
                account.getId()));
//todo
    }
}
