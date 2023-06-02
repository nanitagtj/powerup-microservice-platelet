package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface IDishHandler {
    void createDish(DishRequestDto dishRequestDto, HttpServletRequest request);

}
