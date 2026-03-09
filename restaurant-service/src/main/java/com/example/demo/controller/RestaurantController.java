package com.example.demo.controller;

import com.example.demo.dto.request.CreateRestaurantRequestDTO;
import com.example.demo.dto.response.RestaurantResponseDTO;
import com.example.demo.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantResponseDTO createRestaurant(@RequestBody CreateRestaurantRequestDTO request) {
        return restaurantService.createRestaurant(request);
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getRestaurant(@PathVariable UUID id) {
        return restaurantService.findRestaurantById(id);
    }

    @GetMapping
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantService.findAllRestaurants();
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable UUID id) {
        restaurantService.deleteRestaurant(id);
    }
}