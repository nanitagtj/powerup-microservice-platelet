package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;
    private final IRestaurantServicePort restaurantServicePort;

    @Override
    public void createRestaurant(RestaurantRequestDto restaurantRequestDto, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        restaurantServicePort.createRestaurant(restaurantRequestMapper.toRestaurant(restaurantRequestDto), authorizationHeader);
    }

    @Override
    public RestaurantResponseDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantServicePort.getRestaurantById(id);
        return restaurantResponseMapper.restaurantToRestaurantResponse(restaurant);
    }

    @Override
    public Page<RestaurantDto> getAllRestaurants(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantServicePort.getAllRestaurants(pageable);
        return restaurants.map(restaurantResponseMapper::toResponse);
    }
}
