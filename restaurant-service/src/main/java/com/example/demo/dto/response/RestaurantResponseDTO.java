package com.example.demo.dto.response;


import java.time.Instant;
import java.util.UUID;

public record RestaurantResponseDTO(UUID id,
                                    String name,
                                    Instant openTime,
                                    Instant closeTime) {
}