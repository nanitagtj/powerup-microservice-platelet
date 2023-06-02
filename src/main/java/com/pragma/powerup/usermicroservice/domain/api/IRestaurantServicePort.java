package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantServicePort {
    void createRestaurant(Restaurant restaurant, HttpServletRequest req);
    Restaurant getRestaurantById(Long id);
    Page<Restaurant> getAllRestaurants(Pageable pageable);
}
