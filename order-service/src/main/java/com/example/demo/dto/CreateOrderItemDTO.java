package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderItemDTO(@NotNull @Min(1) Long productId,
                                 @NotNull @Min(1) Integer quantity) {
}
