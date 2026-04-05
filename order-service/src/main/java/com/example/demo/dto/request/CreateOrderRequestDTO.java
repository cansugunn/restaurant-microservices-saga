package com.example.demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequestDTO(
        @NotNull UUID customerId,
        @NotNull UUID restaurantId,
        @Valid List<CreateOrderItemRequestDTO> orderItemDTOList
) {
}
