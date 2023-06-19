package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeRestaurantRequestDto {

    @Column(nullable = false)
    private Long employeeId;
    @Column(nullable = false)
    private Long restaurantId;

}
