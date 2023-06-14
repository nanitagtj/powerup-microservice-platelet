package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface IOrderHandler {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
}
