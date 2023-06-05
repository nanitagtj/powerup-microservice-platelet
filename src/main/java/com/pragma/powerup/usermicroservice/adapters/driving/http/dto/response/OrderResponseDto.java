package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private String status;
    private Long clientId;
    private Long assignedEmployeeId;
    private RestaurantEntity restaurantEntity;
    private List<Dish> dishes;
    private LocalDateTime dateTime;
    private double amount;
}
