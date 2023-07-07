package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import com.pragma.powerup.usermicroservice.domain.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDishRespDto {

    private DishResponseDto dish;
    private int quantity;
    private DishTypeEnum dishTypeEnum;
    private int grams;
    private SoupAccompanimentEnum soupAccompanimentEnum;
    private DessertType dessertType;
    private FlavorTypeEnum flavorTypeEnum;
    private ToppingTypeEnum toppingTypeEnum;
}
