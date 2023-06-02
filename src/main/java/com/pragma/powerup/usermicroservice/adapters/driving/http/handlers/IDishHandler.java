package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface IDishHandler {
    void createDish(DishRequestDto dishRequestDto, HttpServletRequest request);
    void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto, HttpServletRequest request);
}
