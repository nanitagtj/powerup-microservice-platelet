package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;


    @Override
    public void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request) {
        orderServicePort.createOrder(orderRequestMapper.toOrder(orderRequestDto), request);
    }
}
