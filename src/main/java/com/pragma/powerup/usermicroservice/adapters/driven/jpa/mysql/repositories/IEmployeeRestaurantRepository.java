package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRestaurantRepository extends JpaRepository<EmployeeRestaurantEntity, Long> {
    EmployeeRestaurantEntity findByEmployeeId(Long employeeId);

    EmployeeRestaurantEntity findByEmployeeIdAndRestaurantId(Long employeeId, Long restaurantId);

}
