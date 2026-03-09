package com.example.demo.mapper;

import com.example.demo.dto.request.CreateRestaurantRequestDTO;
import com.example.demo.dto.response.RestaurantResponseDTO;
import com.example.demo.entity.Restaurant;
import org.springframework.stereotype.Component;

import static jakarta.persistence.GenerationType.UUID;

@Component
public class RestaurantMapper {

    public Restaurant toEntity(CreateRestaurantRequestDTO dto) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(dto.name());
        restaurant.setOpenTime(dto.openTime());
        restaurant.setCloseTime(dto.closeTime());

        return restaurant;
    }

    public RestaurantResponseDTO toResponse(Restaurant restaurant) {

        return new RestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getOpenTime(),
                restaurant.getCloseTime()
        );
    }

}
