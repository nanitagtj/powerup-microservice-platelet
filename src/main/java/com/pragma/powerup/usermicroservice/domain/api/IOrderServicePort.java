package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;
import com.pragma.powerup.usermicroservice.domain.model.EmployeeRanking;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;

import java.util.List;

public interface IOrderServicePort {
    void createOrder(Order order, Long clientId);
    List<OrderDish> addOrder(Long orderId);
    List<DishTypeEnum> takeOrder();
    List<OrderDish> pendingOrders();
    List<Order> getRestaurantOrder(int pageNumber, int pageSize, String statusOrder, Long idEmployee);

    void updateStatusToReady(Long id, String authorizationHeader);

    void assignEmployeeToOrders(List<Long> orderIds, Long employeeId);

    void updateStatusToDelivered(Long orderId, String pin);

    void cancelOrder(Long orderId, Long clientId);

    void saveOrderLog(OrderLogJson orderLogJson);

    List<OrderLogJson> getOrderLogsByOrderId(Long orderId, Long clientId);

    String calculateElapsedTime(Long orderId, Long ownerId);

    String calculateAverageElapsedTimeByEmployee(Long assignedEmployeeId, Long ownerId);
    List<EmployeeRanking> displayEmployeeRanking(Long ownerId);
    List<OrderDish> getOrderDishes();
}

