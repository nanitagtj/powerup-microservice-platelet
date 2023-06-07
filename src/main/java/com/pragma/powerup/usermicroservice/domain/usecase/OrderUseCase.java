package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderUseCase implements IOrderServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public OrderUseCase(IDishPersistencePort dishPersistencePort, IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Autowired
    JwtProvider jwtProvider;



    public Order createOrder(Order order, HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        String status = "Awaiting";
        String token = request.getHeader("Authorization");
        Long clientId = jwtProvider.getUserIdFromToken(token);

        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(order.getRestaurant().getId());

        Map<Dish, Long> dishQuantities = new HashMap<>();
        double amount = 0.0;

        for (Map.Entry<Dish, Long> entry : order.getDishQuantities().entrySet()) {
            Dish dish = entry.getKey();
            Long quantity = entry.getValue();
            Dish persistedDish = dishPersistencePort.getDishById(dish.getId());
            dishQuantities.put(persistedDish, quantity);

            double dishPrice = persistedDish.getPrice();
            amount += dishPrice * quantity;
        }

        Order newOrder = new Order();
        newOrder.setClientId(clientId);
        newOrder.setRestaurant(restaurant);
        newOrder.setDishQuantities(dishQuantities);
        newOrder.setDateTime(dateTime);
        newOrder.setStatus(status);
        newOrder.setAmount(amount);

        return orderPersistencePort.saveOrder(newOrder);
    }

}
