package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.usermicroservice.domain.model.Dish;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class OrderRequestDto {

    @Column(nullable = false)
    private Long restaurantId;
    @Column(nullable = false)
    private List<Long> dishesId;

}
