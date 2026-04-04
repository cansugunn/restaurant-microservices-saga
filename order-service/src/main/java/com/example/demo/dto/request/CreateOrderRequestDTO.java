package com.example.demo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequestDTO(@NotNull @Min(1) Long restaurantId,
                                    @Valid List<CreateOrderItemRequestDTO> orderItemDTOList) {
}
