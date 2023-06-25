package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
    List<OrderResponseDto> getRestaurantOrders(int pageNumber, int pageSize, String statusOrder);

    void assignEmployeeToOrder(List<Long> orderIds, HttpServletRequest request);
}
