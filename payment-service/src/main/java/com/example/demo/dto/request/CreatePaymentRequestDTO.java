package com.example.demo.dto.request;

import java.math.BigDecimal;

public record CreatePaymentRequestDTO(
        Long orderId,
        BigDecimal amount,
        Long accountId

) {
}
