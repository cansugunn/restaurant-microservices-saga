package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "restaurant")
public class Restaurant {
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    private Instant openTime;

    private Instant closeTime;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Product> productsList;
}
