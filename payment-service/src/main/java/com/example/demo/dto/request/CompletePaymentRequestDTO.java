package com.example.demo.dto.request;

import java.util.UUID;

public record CompletePaymentRequestDTO(Long id,
                                        UUID transactionId) {
}
