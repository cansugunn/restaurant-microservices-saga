package com.example.demo.dto.response;

import com.example.demo.entity.OrderStatus;

import java.util.List;
import java.util.UUID;

public record GetOrderResponseDTO(UUID orderTrackingId,
                                  OrderStatus orderStatus,
                                  List<String> failureMessages) {
}
