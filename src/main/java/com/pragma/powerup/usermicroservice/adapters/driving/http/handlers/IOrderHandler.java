package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
    List<OrderResponseDto> getRestaurantOrders(int pageNumber, int pageSize, String statusOrder);

    void updateStatusToReady(Long id, HttpServletRequest request);

    void assignEmployeeToOrder(List<Long> orderIds, HttpServletRequest request);

    void updateStatusToDelivered(Long orderId, String pin);

    void cancelOrder(Long orderId, HttpServletRequest request);
    List<OrderLogJson> getOrderLogsByOrderId(Long orderId, HttpServletRequest request);
}
