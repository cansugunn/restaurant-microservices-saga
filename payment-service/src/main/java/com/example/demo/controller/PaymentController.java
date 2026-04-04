package com.example.demo.controller;

import com.example.demo.dto.response.PaymentResponseDTO;
import com.example.demo.dto.request.CreatePaymentRequestDTO;
import com.example.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponseDTO createPayment(@RequestBody CreatePaymentRequestDTO request) {
        return paymentService.createPayment(request);
    }

    @GetMapping("/{paymentId}")
    public PaymentResponseDTO getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @GetMapping("/by-order/{orderId}")
    public PaymentResponseDTO getPaymentByOrderId(@PathVariable Long orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

    @PostMapping("/{paymentId}/complete")
    public PaymentResponseDTO completePayment(@PathVariable Long paymentId) {
        return paymentService.completePayment(paymentId);
    }

    @PostMapping("/{paymentId}/fail")
    public PaymentResponseDTO failPayment(@PathVariable Long paymentId) {
        return paymentService.failPayment(paymentId);
    }
}