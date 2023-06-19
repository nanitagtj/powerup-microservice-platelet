package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.EmployeeRestaurant;
import jakarta.servlet.http.HttpServletRequest;

public interface IEmployeeRestaurantServicePort {
    void assignEmployeeToRestaurant(EmployeeRestaurant employeeRestaurant, HttpServletRequest request);
}
