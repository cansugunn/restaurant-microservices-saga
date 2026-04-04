package com.example.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderItemRequestDTO(@NotNull @Min(1) Long productId,
                                        @NotNull @Min(1) Integer quantity) {
}
