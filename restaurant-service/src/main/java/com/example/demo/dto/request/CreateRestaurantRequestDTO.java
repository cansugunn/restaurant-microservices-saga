package com.example.demo.dto.request;

import java.time.Instant;

public record CreateRestaurantRequestDTO(
        String name,
        Instant openTime,
        Instant closeTime
) {
}