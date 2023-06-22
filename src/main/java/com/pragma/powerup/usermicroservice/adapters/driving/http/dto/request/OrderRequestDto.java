package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class OrderRequestDto {

    @Column(nullable = false)
    private Long restaurantId;
    private List<OrderDishReqDto> orderDishes;

}
