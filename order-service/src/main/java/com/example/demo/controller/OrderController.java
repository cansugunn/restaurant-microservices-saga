package com.example.demo.controller;

import com.example.demo.dto.request.CreateOrderRequestDTO;
import com.example.demo.dto.response.CreateOrderResponseDTO;
import com.example.demo.dto.response.GetOrderResponseDTO;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(dto));
    }

    @GetMapping(path="/{trackingId}")
    public ResponseEntity<GetOrderResponseDTO> getOrder(@PathVariable UUID trackingId){
        return ResponseEntity.ok(orderService.findOrderByTrackingId(trackingId));
    }
}
