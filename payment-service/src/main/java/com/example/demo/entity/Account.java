package com.example.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Account {

    @Id
    private Long id;

    @Column(name = "customer_id")
    private UUID customerId;


    @Column(name = "balance")
    private BigDecimal balance;






}
