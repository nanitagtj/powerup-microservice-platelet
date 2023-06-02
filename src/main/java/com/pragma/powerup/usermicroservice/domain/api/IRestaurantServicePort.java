package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import jakarta.servlet.http.HttpServletRequest;

public interface IRestaurantServicePort {
    void createRestaurant(Restaurant restaurant, HttpServletRequest req);
    Restaurant getRestaurantById(Long id);
}
