package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IEmployeeRestaurantHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IEmployeeRestaurantRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IEmployeeRestaurantServicePort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRestaurantHandlerImpl implements IEmployeeRestaurantHandler {

    private final IEmployeeRestaurantServicePort employeeRestaurantServicePort;
    private final IEmployeeRestaurantRequestMapper employeeRestaurantRequestMapper;

    @Override
    public void assignEmployeeToRestaurant(EmployeeRestaurantRequestDto employeeRestaurantRequestDto, HttpServletRequest request) {
        employeeRestaurantServicePort.assignEmployeeToRestaurant(employeeRestaurantRequestMapper.toEmployeeRestaurant(employeeRestaurantRequestDto), request);
    }

}
