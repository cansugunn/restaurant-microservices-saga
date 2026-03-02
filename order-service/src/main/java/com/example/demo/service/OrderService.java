package com.example.demo.service;

import com.example.demo.dto.CreateOrderRequestDTO;
import com.example.demo.dto.CreateOrderResponseDTO;
import com.example.demo.dto.GetOrderResponseDTO;
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

    public CreateOrderResponseDTO  createOrder(@Valid CreateOrderRequestDTO requestDto){
        Order order = orderMapper.toOrder(requestDto);
        orderRepository.save(order);
        return orderMapper.toCreateOrderResponseDTO(order);
    }

    public GetOrderResponseDTO findOrderByTrackingId(@NotNull UUID trackingId) {
        Order order=orderRepository.findByTrackingId(trackingId);

        return orderMapper.toGetOrderResponseDTO(order);

        //todo find order
        //todo map order to get order response
        //todo return response

    }
}
