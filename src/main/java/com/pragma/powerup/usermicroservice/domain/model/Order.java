package com.pragma.powerup.usermicroservice.domain.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private String status;
    private Long clientId;
    private Long assignedEmployeeId;
    private Restaurant idRestaurant;
    private List<OrderDish> orderDishes;
    private LocalDateTime dateTime;
    private double amount;

    public Order() {
    }

    public Order(Long id, String status, Long clientId, Long assignedEmployeeId, Restaurant idRestaurant, List<OrderDish> orderDishes, LocalDateTime dateTime, double amount) {
        this.id = id;
        this.status = status;
        this.clientId = clientId;
        this.assignedEmployeeId = assignedEmployeeId;
        this.idRestaurant = idRestaurant;
        this.orderDishes = orderDishes != null ? orderDishes : new ArrayList<>();
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Order(long l, String awaiting, long l1, long l2, long l3) {
    }

    public Order(Long orderId) {
        this.id = orderId;
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

    public Restaurant getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Restaurant idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
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