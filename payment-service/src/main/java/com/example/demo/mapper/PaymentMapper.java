package com.example.demo.mapper;

import com.example.demo.dto.response.PaymentResponseDTO;
import com.example.demo.dto.request.CreatePaymentRequestDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Payment;
import com.example.demo.entity.PaymentStatus;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class PaymentMapper {
    private final AccountRepository accountRepository;

    public Payment toEntity(CreatePaymentRequestDTO dto, Account account) {
        Payment payment = new Payment();

        payment.setOrderId(dto.orderId());
        payment.setAmount(dto.amount());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAccount(account);
        payment.setCreatedAt(Instant.now());

        return payment;
    }

    public void completePayment(Payment payment) {
        payment.setStatus(PaymentStatus.COMPLETED);
    }

    public void failPayment(Payment payment) {
        payment.setStatus(PaymentStatus.FAILED);
    }

    public PaymentResponseDTO toResponse(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrderId(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }
}
