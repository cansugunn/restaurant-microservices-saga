package com.example.demo.repository;

import com.example.demo.entity.ProductPriceView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceViewRepository extends JpaRepository<ProductPriceView, Long> {
}
