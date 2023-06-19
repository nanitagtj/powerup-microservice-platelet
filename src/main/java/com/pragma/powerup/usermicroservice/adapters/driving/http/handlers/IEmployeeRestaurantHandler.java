package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRestaurantRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface IEmployeeRestaurantHandler {

    void assignEmployeeToRestaurant(EmployeeRestaurantRequestDto employeeRestaurantRequestDto, HttpServletRequest request);
}
