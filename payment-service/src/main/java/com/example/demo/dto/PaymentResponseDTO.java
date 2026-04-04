package com.example.demo.dto;

import com.example.demo.entity.PaymentStatus;

import java.time.Instant;

public record PaymentResponseDTO(
        Long id,
        Long orderId,
        PaymentStatus paymentStatus,
        Instant createdAt
) {
}
