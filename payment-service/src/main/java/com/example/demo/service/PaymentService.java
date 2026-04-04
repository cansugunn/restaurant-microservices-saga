package com.example.demo.service;


import com.example.demo.dto.response.PaymentResponseDTO;
import com.example.demo.dto.request.CreatePaymentRequestDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Payment;
import com.example.demo.mapper.PaymentMapper;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PaymentMapper paymentMapper;

    public PaymentResponseDTO createPayment(CreatePaymentRequestDTO dto) {
        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Payment payment = paymentMapper.toEntity(dto, account);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(savedPayment);
    }

    public PaymentResponseDTO getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(paymentMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public PaymentResponseDTO getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(paymentMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public PaymentResponseDTO completePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentMapper.completePayment(payment);
        Payment updatedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(updatedPayment);
    }

    public PaymentResponseDTO failPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paymentMapper.failPayment(payment);
        Payment updatedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(updatedPayment);
    }
}
