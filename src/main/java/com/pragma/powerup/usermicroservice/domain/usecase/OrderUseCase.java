package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.spi.IOrderPersistencePort;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    public Order createOrder(Order order, HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        double amount = calculateAmount(order.getDishes());
        String status = "In progress";
        Long restaurantId = order.getRestaurant().getId();

        Order newOrder = new Order(
                order.getClientId(),
                restaurantId,
                order.getDishes(),
                dateTime,
                amount,
                status
        );

        return orderPersistencePort.saveOrder(newOrder);
    }

    private double calculateAmount(List<Dish> dishes) {
        double amount = 0.0;
        for (Dish dish : dishes) {
            amount += dish.getPrice();
        }
        return amount;
    }
}
