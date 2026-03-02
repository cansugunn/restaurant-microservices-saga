package com.example.demo.mapper;

import com.example.demo.dto.CreateOrderItemDTO;
import com.example.demo.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemMapper {
    public OrderItem toOrderItem(CreateOrderItemDTO createOrderItemDTO){
        OrderItem orderItem = new OrderItem();

        orderItem.setProductId(createOrderItemDTO.productId());
        orderItem.setQuantity(createOrderItemDTO.quantity());
        orderItem.setPrice(new BigDecimal(0)); //todo restauranttan alacağız

        BigDecimal totalAmount = calculateTotalAmount(orderItem.getPrice(), orderItem.getQuantity());
        orderItem.setTotalAmount(totalAmount);

        return orderItem;
    }

    private BigDecimal calculateTotalAmount(BigDecimal price, Integer quantity){
        BigDecimal quantityAsBigDecimal = new BigDecimal(quantity);
        return  price.multiply(quantityAsBigDecimal);
    }
}
