package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IOrderServicePort {
    void createOrder(Order order, HttpServletRequest request);

    List<Order> getRestaurantOrder(int pageNumber, int pageSize, String statusOrder, Long idEmployee);

    void updateStatusToReady(Long id, HttpServletRequest request);
    void updateStatusToDelivered(Long orderId, String pin);
    void assignEmployeeToOrders(List<Long> orderIds, Long employeeId);
}

