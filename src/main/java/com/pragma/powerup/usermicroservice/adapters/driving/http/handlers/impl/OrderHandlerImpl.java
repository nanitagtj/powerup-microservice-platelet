package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;
    private final IOrderResponseMapper orderResponseMapper;


    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request) {
        Order order = orderRequestMapper.toOrder(orderRequestDto);
        Order createdOrder = orderServicePort.createOrder(order, request);
        return orderResponseMapper.toOrderResponseDto(createdOrder);
    }

    @Override
    public Page<OrderResponseDto> getOrdersByStatusAndRestaurant(String status, Long restaurantId, Pageable pageable, HttpServletRequest request) throws AuthenticationException {
        Page<Order> orderPage = orderServicePort.getOrdersByStatusAndRestaurant(status, restaurantId, pageable, request);
        return orderPage.map(orderResponseMapper::toOrderResponseDto);
    }
}
