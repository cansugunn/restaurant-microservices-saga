package com.example.demo.mapper;

import com.example.demo.dto.request.CreateOrderItemRequestDTO;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.ProductPriceView;
import com.example.demo.repository.ProductPriceViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {
    private final ProductPriceViewRepository productPriceViewRepository;

    public OrderItem toOrderItem(CreateOrderItemRequestDTO createOrderItemRequestDTO) {
        OrderItem orderItem = new OrderItem();

        orderItem.setProductId(createOrderItemRequestDTO.productId());
        orderItem.setQuantity(createOrderItemRequestDTO.quantity());
        
        ProductPriceView productPriceView = productPriceViewRepository.findById(createOrderItemRequestDTO.productId())
                .orElseThrow(() -> new RuntimeException("Product not found in view for id: " + createOrderItemRequestDTO.productId()));
        
        orderItem.setPrice(productPriceView.getPrice());

        BigDecimal totalAmount = calculateTotalAmount(orderItem.getPrice(), orderItem.getQuantity());
        orderItem.setTotalAmount(totalAmount);

        return orderItem;
    }

    private BigDecimal calculateTotalAmount(BigDecimal price, Integer quantity) {
        BigDecimal quantityAsBigDecimal = new BigDecimal(quantity);
        return price.multiply(quantityAsBigDecimal);
    }
}
