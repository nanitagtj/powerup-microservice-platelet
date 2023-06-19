package com.pragma.powerup.usermicroservice.domain.model;

public class EmployeeRestaurant {

    private Long id;
    private Long employeeId;
    private Restaurant restaurant;

    public EmployeeRestaurant() {
    }

    public EmployeeRestaurant(Long id, Long employeeId, Restaurant restaurant) {
        this.id = id;
        this.employeeId = employeeId;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
