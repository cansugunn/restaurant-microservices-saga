package com.example.demo.service;

import com.example.common.kafka.EventPublisher;
import com.example.common.kafka.config.KafkaProperties;
import com.example.common.kafka.event.OrderCreatedEvent;
import com.example.demo.dto.request.CreateOrderRequestDTO;
import com.example.demo.dto.response.CreateOrderResponseDTO;
import com.example.demo.dto.response.GetOrderResponseDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.OrderRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.UUID;


@Slf4j
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

        // order-created event yayımla → Payment Service dinler
        eventPublisher.publish(
                kafkaProperties.getTopics().getOrderCreated().getName(),
                new OrderCreatedEvent(
                        requestDto.customerId(),
                        order.getId(),
                        order.getPrice(),
                        order.getRestaurantId(),
                        Instant.now()
                ));

        log.info("Order created and event published. orderId={}, status={}", order.getId(), order.getOrderStatus());
        return orderMapper.toCreateOrderResponseDTO(order);
    }

    public GetOrderResponseDTO findOrderByTrackingId(@NotNull UUID trackingId) {
        Order order = orderRepository.findByTrackingId(trackingId);
        return orderMapper.toGetOrderResponseDTO(order);
    }

    // Payment başarısız → order'ı CANCELLED yap
    public void cancelOrder(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setFailureMessages(reason);
        orderRepository.save(order);
        log.info("Order CANCELLED. orderId={}, reason={}", orderId, reason);
    }

    // Restaurant onayı → order'ı SUCCESS yap
    public void approveOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        order.setOrderStatus(OrderStatus.SUCCESS);
        orderRepository.save(order);
        log.info("Order SUCCESS. orderId={}", orderId);
    }
}
