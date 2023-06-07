package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IDishPersistencePort dishPersistencePort, IOrderPersistencePort orderPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
    }

    @Autowired
    JwtProvider jwtProvider;



    public Order createOrder(Order order, HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        double amount = calculateAmount(order.getDishes());
        String status = "In progress";
        String token = request.getHeader("Authorization");
        Long clientId = jwtProvider.getUserIdFromToken(token);

        List<Long> dishId = new ArrayList<>();
        for (Dish dish : order.getDishes()) {
            dishId.add(dish.getId());
        }
        List<Dish> dishes = dishPersistencePort.getDishesById(dishId);

        Order newOrder = new Order();
        newOrder.setClientId(clientId);
        newOrder.setRestaurant(order.getRestaurant());
        newOrder.setDishes(dishes);
        newOrder.setDateTime(dateTime);
        newOrder.setAmount(amount);
        newOrder.setStatus(status);
        newOrder.setAssignedEmployeeId(null);

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
