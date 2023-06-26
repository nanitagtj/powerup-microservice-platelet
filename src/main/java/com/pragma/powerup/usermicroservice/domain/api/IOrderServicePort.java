package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IOrderServicePort {
    void createOrder(Order order, HttpServletRequest request);
    List<Order> getRestaurantOrder(int pageNumber, int pageSize, String statusOrder, Long idEmployee);

    void updateStatusToReady(Long id, HttpServletRequest request);

    void assignEmployeeToOrders(List<Long> orderIds, Long employeeId);

    void updateStatusToDelivered(Long orderId, String pin);

    void cancelOrder(Long orderId, Long clientId);

    void saveOrderLog(OrderLogJson orderLogJson);

    List<OrderLogJson> getOrderLogsByOrderId(Long orderId, Long clientId);

    String calculateElapsedTime(Long orderId, Long ownerId);

    String calculateAverageElapsedTimeByEmployee(Long assignedEmployeeId, Long ownerId);
}

