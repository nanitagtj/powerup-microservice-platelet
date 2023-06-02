package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Dish;
import jakarta.servlet.http.HttpServletRequest;

public interface IDishServicePort {

    void createDish(Dish dish, HttpServletRequest req);
}
