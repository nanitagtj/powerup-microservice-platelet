package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface IRestaurantHandler {
    void createRestaurant(RestaurantRequestDto restaurantRequestDto, HttpServletRequest request);
}
