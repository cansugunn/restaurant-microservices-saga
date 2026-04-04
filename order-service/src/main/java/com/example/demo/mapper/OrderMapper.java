package com.example.demo.mapper;

import com.example.demo.dto.CreateOrderRequestDTO;
import com.example.demo.dto.CreateOrderResponseDTO;
import com.example.demo.dto.GetOrderResponseDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    public Order toOrder(CreateOrderRequestDTO requestDto) {
        Order order = new Order();

        order.setTrackingId(UUID.randomUUID());
        order.setRestaurantId(requestDto.restaurantId());
        order.setOrderStatus(OrderStatus.PENDING);

        List<OrderItem> orderItemList = requestDto.orderItemDTOList()
                .stream()
                .map(orderItemMapper::toOrderItem)
                .toList();
        order.setOrderItemList(orderItemList);

        BigDecimal totalAmount = calculateTotalAmount(orderItemList);
        order.setPrice(totalAmount);

        return order;
    }

    public CreateOrderResponseDTO toCreateOrderResponseDTO(Order order) {
        return new CreateOrderResponseDTO(order.getTrackingId(), order.getOrderStatus());
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        BigDecimal price = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            price.add(orderItem.getTotalAmount());
        }
        return price;
    }


    //todo failure messages should be fixed
    public GetOrderResponseDTO toGetOrderResponseDTO(Order order) {
        List<String> failureMessageList = Collections.EMPTY_LIST;
        if (order.getFailureMessages() != null && !order.getFailureMessages().isBlank()) {
            failureMessageList = Arrays.stream(order.getFailureMessages().split(",")).toList();
        }

        return new GetOrderResponseDTO(order.getTrackingId(), order.getOrderStatus(), failureMessageList);
    }
}
