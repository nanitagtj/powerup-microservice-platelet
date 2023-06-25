package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);
    List<Order> getOrdersByClientId(Long clientId);
    List<Order> getRestaurantOrder(int pageNumber, int pageSize, String status, Long restaurantId);
    Order getOrderById(Long orderId);
    void saveOrderAll(List<Order> orders);
}
