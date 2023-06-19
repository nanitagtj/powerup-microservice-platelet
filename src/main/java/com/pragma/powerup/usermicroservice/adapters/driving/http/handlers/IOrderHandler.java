package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.naming.AuthenticationException;

public interface IOrderHandler {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
    Page<OrderResponseDto> getOrdersByStatusAndRestaurant( String status, Long restaurantId, Pageable pageable, HttpServletRequest request) throws AuthenticationException;
}
