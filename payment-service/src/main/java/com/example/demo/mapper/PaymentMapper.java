package com.example.demo.mapper;

import com.example.demo.dto.PaymentResponseDTO;
import com.example.demo.dto.request.CompletePaymentRequestDTO;
import com.example.demo.dto.request.CreatePaymentRequestDTO;
import com.example.demo.dto.request.FailedPaymentRequestDTO;
import com.example.demo.entity.Payment;
import com.example.demo.entity.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class PaymentMapper {

    public Payment toEntity(CreatePaymentRequestDTO dto) {

        Payment payment = new Payment();

        payment.setOrderId(dto.orderId());
        payment.setAmount(dto.amount());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(Instant.now());

        return payment;
    }

    public void completePayment(Payment payment, CompletePaymentRequestDTO dto) {

        payment.setStatus(PaymentStatus.COMPLETED);

    }

    public void failPayment(Payment payment, FailedPaymentRequestDTO dto) {

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
