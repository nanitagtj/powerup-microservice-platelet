package com.pragma.powerup.usermicroservice.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Order {
    private Long id;
    private String status;
    private Long clientId;
    private Long assignedEmployeeId;
    private Restaurant restaurant;
    private Map<Dish, Long> dishQuantities;
    private LocalDateTime dateTime;
    private double amount;

    public Order() {
    }

    public Order(Long id, String status, Long clientId, Long assignedEmployeeId, Restaurant restaurant, Map<Dish, Long> dishQuantities, LocalDateTime dateTime, double amount) {
        this.id = id;
        this.status = status;
        this.clientId = clientId;
        this.assignedEmployeeId = assignedEmployeeId;
        this.restaurant = restaurant;
        this.dishQuantities = dishQuantities;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getAssignedEmployeeId() {
        return assignedEmployeeId;
    }

    public void setAssignedEmployeeId(Long assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Map<Dish, Long> getDishQuantities() {
        return dishQuantities;
    }

    public void setDishQuantities(Map<Dish, Long> dishQuantities) {
        this.dishQuantities = dishQuantities;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}