package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.usermicroservice.domain.model.EmployeeRestaurant;

import java.util.List;

public interface IEmployeeRestaurantPersistencePort {
    EmployeeRestaurant saveEmployeeRestaurant(EmployeeRestaurant employeeRestaurant);
    boolean isEmployeeAssignedToOtherRestaurant(Long employeeId, Long restaurantId);

    boolean isEmployeeAssignedToRestaurant(Long employeeId, Long restaurantId);

    EmployeeRestaurantEntity getRestaurantEmployee(Long employeeId);

    List<EmployeeRestaurant> getEmployeeRestaurantsByRestaurantId(Long restaurantId);
}
