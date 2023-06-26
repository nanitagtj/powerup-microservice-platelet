package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.usermicroservice.domain.model.EmployeeRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmployeeRestaurantRepository extends JpaRepository<EmployeeRestaurantEntity, Long> {
    EmployeeRestaurantEntity findByEmployeeId(Long employeeId);

    EmployeeRestaurantEntity findByEmployeeIdAndRestaurantId(Long employeeId, Long restaurantId);

    List<EmployeeRestaurantEntity> findByRestaurantId(Long restaurantId);
}
