package com.example.demo.service;

import com.example.demo.dto.request.CreateRestaurantRequestDTO;
import com.example.demo.dto.response.RestaurantResponseDTO;
import com.example.demo.entity.Restaurant;
import com.example.demo.mapper.RestaurantMapper;
import com.example.demo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;

    public RestaurantResponseDTO createRestaurant(CreateRestaurantRequestDTO dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.toResponse(savedRestaurant);

    }

    public RestaurantResponseDTO findRestaurantById(UUID id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return restaurantMapper.toResponse(restaurant);
    }

    public List<RestaurantResponseDTO> findAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toResponse)
                .toList();
    }

    public void deleteRestaurant(UUID id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurantRepository.delete(restaurant);
    }


}
