package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.EmployeeRestaurant;

public interface IEmployeeRestaurantPersistencePort {
    EmployeeRestaurant saveEmployeeRestaurant(EmployeeRestaurant employeeRestaurant);
    boolean isEmployeeAssignedToOtherRestaurant(Long employeeId, Long restaurantId);

    boolean isEmployeeAssignedToRestaurant(Long employeeId, Long restaurantId);
}
