package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.clientapi.IOrderLogClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.EmployeeAssignedToTheSameRestaurantException;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidOrderStatusException;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidPageNumberException;
import com.pragma.powerup.usermicroservice.domain.model.*;
import com.pragma.powerup.usermicroservice.domain.orderMessage.OrderLogJsonSerialize;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IOrderPersistencePort;

import java.time.LocalDateTime;
import java.util.List;

public class OrderUseCaseValidations {
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IOrderLogClientPort orderLogClientPort;

    public OrderUseCaseValidations(IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort, IOrderLogClientPort orderLogClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.orderLogClientPort = orderLogClientPort;
    }

    public boolean hasInProgressOrder(Long clientId) {
        List<Order> clientOrders = orderPersistencePort.getOrdersByClientId(clientId);

        for (Order order : clientOrders) {
            if (order.getStatus().equalsIgnoreCase("In process") || order.getStatus().equalsIgnoreCase("Awaiting")) {
                return true;
            }
        }

        return false;
    }

    public void validateRange(int pageNumber, int pageSize) {
        if (pageNumber <= 0 || pageSize <= 0) {
            throw new InvalidPageNumberException();
        }
    }

    public double calculateTotalAmount(List<OrderDish> orderDishes) {
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
    public void checkAndAssignOrderToEmployee(Order order, Long employeeId, List<Order> orders) {
        if (order.getAssignedEmployeeId() != null) {
            throw new EmployeeAssignedToTheSameRestaurantException();
        }

        if (!order.getStatus().equalsIgnoreCase("awaiting")) {
            throw new InvalidOrderStatusException();
        }

        assignOrderToEmployee(order, employeeId, orders);
        saveOrderLogForAssignedEmployee(order.getId());
    }
    public void assignOrderToEmployee(Order order, Long employeeId, List<Order> orders) {
        order.setAssignedEmployeeId(employeeId);
        order.setStatus("In process");
        orders.add(order);
    }
    public void saveOrderLog(Long orderId, String previousStatus, String newStatus) {
        OrderLogJson orderLog = new OrderLogJson();
        orderLog.setOrderId(orderId);
        orderLog.setPreviousStatus(previousStatus);
        orderLog.setNewStatus(newStatus);
        orderLog.setTimestamp(LocalDateTime.now());

        orderLogClientPort.saveOrderLog(OrderLogJsonSerialize.serializeToJson(orderLog));
    }

    public void saveOrderLogForCreateOrder(Long orderId) {
        saveOrderLog(orderId, "", "Awaiting");
    }

    public void saveOrderLogForAssignedEmployee(Long orderId) {
        saveOrderLog(orderId, "Awaiting", "In process");
    }

    public void saveOrderLogForUpdateStatusToReady(Long orderId) {
        saveOrderLog(orderId, "In process", "Ready");
    }

}
