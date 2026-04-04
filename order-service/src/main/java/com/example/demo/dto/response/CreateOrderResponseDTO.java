package com.example.demo.dto.response;

import com.example.demo.entity.OrderStatus;

import java.util.UUID;


public record CreateOrderResponseDTO(UUID orderTrackingId, OrderStatus orderStatus) {
}
