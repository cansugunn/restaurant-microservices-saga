package com.example.demo.service;

import com.example.common.kafka.EventPublisher;
import com.example.common.kafka.config.KafkaProperties;
import com.example.common.kafka.event.OrderCreatedEvent;
import com.example.demo.dto.request.CreateOrderRequestDTO;
import com.example.demo.dto.response.CreateOrderResponseDTO;
import com.example.demo.dto.response.GetOrderResponseDTO;
import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.OrderRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;


@Service
@Validated
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;
    private final KafkaProperties kafkaProperties;

    public CreateOrderResponseDTO createOrder(@Valid CreateOrderRequestDTO requestDto) {
        Order order = orderMapper.toOrder(requestDto);
        orderRepository.save(order);
        //todo map to created order event
        //todo bu örnek silinecek
        eventPublisher.publish(
                kafkaProperties.getTopics().getOrderCreated().getName(),
                new OrderCreatedEvent(
                        UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"), //todo dtode olup ordan alınacak
                        order.getId(),
                        order.getPrice()));
        //todo
        return orderMapper.toCreateOrderResponseDTO(order);
    }

    public GetOrderResponseDTO findOrderByTrackingId(@NotNull UUID trackingId) {
        Order order = orderRepository.findByTrackingId(trackingId);

        return orderMapper.toGetOrderResponseDTO(order);

        //todo find order
        //todo map order to get order response
        //todo return response

    }
}
