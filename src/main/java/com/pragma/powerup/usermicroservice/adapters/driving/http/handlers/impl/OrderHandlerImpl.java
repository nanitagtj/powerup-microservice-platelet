package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;
    private final IOrderResponseMapper orderResponseMapper;
    private final HttpServletRequest request;
    private final JwtProvider jwtProvider;


    @Override
    public void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request) {
        orderServicePort.createOrder(orderRequestMapper.toOrder(orderRequestDto), request);
    }

    @Override
    public List<OrderResponseDto> getRestaurantOrders(int pageNumber, int pageSize, String statusOrder) {
        return orderResponseMapper.toOrderRes(orderServicePort.getRestaurantOrder(pageNumber, pageSize, statusOrder, jwtProvider.getUserIdFromToken(request.getHeader("Authorization"))));
    }

    @Override
    public void assignEmployeeToOrder(Long orderId, HttpServletRequest request) {
        Long employeeId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        orderServicePort.assignEmployeeToOrder(orderId, employeeId);
    }
}
