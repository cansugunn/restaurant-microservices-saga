package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

/**
 * Maps to a database view that provides access to product prices
 * across microservices synchronously without REST calls.
 */
@Entity
@Immutable
@Table(name = "product_price_view")
@Getter
public class ProductPriceView {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    protected ProductPriceView() {
        // JPA requires no-args constructor
    }
}
