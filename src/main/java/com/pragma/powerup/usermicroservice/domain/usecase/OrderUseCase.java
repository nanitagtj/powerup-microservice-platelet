package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderInProgressException;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import com.pragma.powerup.usermicroservice.domain.spi.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;

    public OrderUseCase(IOrderDishPersistencePort orderDishPersistencePort, IOrderPersistencePort orderPersistencePort, IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, IDishPersistencePort dishPersistencePort) {
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
    }

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public void createOrder(Order order, HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        String status = "Awaiting";
        String token = request.getHeader("Authorization");
        Long clientId = jwtProvider.getUserIdFromToken(token);

        if (hasInProgressOrder(clientId)) {
            throw new OrderInProgressException();
        }

        order.setClientId(clientId);
        order.setDateTime(dateTime);
        order.setStatus(status);
        double amount = calculateTotalAmount(order.getOrderDishes());
        order.setAmount(amount);
        Order orderEntity = orderPersistencePort.saveOrder(order);

        order.getOrderDishes().forEach(orderDish -> orderDish.setOrder(orderEntity));
        orderDishPersistencePort.saveOrderDish(order.getOrderDishes());
    }

    private double calculateTotalAmount(List<OrderDish> orderDishes) {
        double totalAmount = 0.0;
        for (OrderDish orderDish : orderDishes) {
            Dish dish = orderDish.getDish();
            Dish persistedDish = dishPersistencePort.getDishById(dish.getId());
            if (persistedDish != null) {
                totalAmount += persistedDish.getPrice() * orderDish.getQuantity();
            }
        }
        return totalAmount;
    }

    private boolean hasInProgressOrder(Long clientId) {
        List<Order> clientOrders = orderPersistencePort.getOrdersByClientId(clientId);

        for (Order order : clientOrders) {
            if (order.getStatus().equalsIgnoreCase("In process") || order.getStatus().equalsIgnoreCase("Awaiting") || order.getStatus().equalsIgnoreCase("Ready")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Order> getRestaurantOrder(int pageNumber, int pageSize, String status, Long employeeId) {
        validateRange(pageNumber, pageSize);
        pageNumber -= 1;
        Long idRestaurant = employeeRestaurantPersistencePort.getRestaurantEmployee(employeeId).getRestaurant().getId();
        List<OrderDish> orderDishes = orderDishPersistencePort.getRestaurantOrderDish(status, idRestaurant);
        List<Order> orders = orderPersistencePort.getRestaurantOrder(pageNumber, pageSize, status, idRestaurant);

        for (Order order : orders) {
            for (OrderDish orderDish : orderDishes) {
                if (order.getId() != null && order.getId().equals(orderDish.getOrder().getId())) {
                    if (order.getOrderDishes() == null) {
                        order.setOrderDishes(new ArrayList<>());
                    }
                    order.getOrderDishes().add(orderDish);
                }
            }
        }

        return orders;
    }

    private void validateRange(int pageNumber, int pageSize) {
        if (pageNumber <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("La página y el tamaño de página deben ser mayores que cero.");
        }
    }

}
