package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "transaction_id")
    private UUID transactionId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;



    //todo kafka order->payments


}
