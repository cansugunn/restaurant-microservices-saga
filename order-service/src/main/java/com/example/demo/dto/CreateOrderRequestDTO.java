package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequestDTO(@NotNull @Min(1) Long restaurantId,
                                    @Valid List<CreateOrderItemDTO> orderItemDTOList) {
}
