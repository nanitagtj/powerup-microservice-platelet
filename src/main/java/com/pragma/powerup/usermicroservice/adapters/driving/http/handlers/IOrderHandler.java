package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
    List<OrderResponseDto> getRestaurantOrders(int pageNumber, int pageSize, String statusOrder);

    void assignEmployeeToOrder(Long orderId, HttpServletRequest request);
    void updateStatusToReady(Long id, HttpServletRequest request);
    void updateStatusToDelivered(Long orderId, String pin);
}
