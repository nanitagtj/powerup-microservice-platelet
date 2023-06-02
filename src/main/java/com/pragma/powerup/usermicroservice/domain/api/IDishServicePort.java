package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Dish;
import jakarta.servlet.http.HttpServletRequest;

public interface IDishServicePort {

    void createDish(Dish dish, HttpServletRequest req);
    void updateDishStatus(Long id, boolean active, HttpServletRequest request);
    void updateDish(Long id, Dish dish, HttpServletRequest request);
}
