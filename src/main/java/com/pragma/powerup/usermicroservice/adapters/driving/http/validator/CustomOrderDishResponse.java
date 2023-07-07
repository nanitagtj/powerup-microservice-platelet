package com.pragma.powerup.usermicroservice.adapters.driving.http.validator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.usermicroservice.domain.enums.*;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomOrderDishResponse {
    private String dishName;
    private DishTypeEnum dishTypeEnum;
    private Integer grams;
    private SoupAccompanimentEnum soupAccompanimentEnum;
    private DessertType dessertType;
    private FlavorTypeEnum flavorTypeEnum;
    private ToppingTypeEnum toppingTypeEnum;

    public CustomOrderDishResponse(OrderDish orderDish) {
        this.dishName = orderDish.getDish().getName();
        this.dishTypeEnum = orderDish.getDishTypeEnum();
        this.grams = orderDish.getGrams();
        this.soupAccompanimentEnum = orderDish.getSoupAccompanimentEnum();
        this.dessertType = orderDish.getDessertType();
        this.flavorTypeEnum = orderDish.getFlavorTypeEnum();
        this.toppingTypeEnum = orderDish.getToppingTypeEnum();
    }
    public static CustomOrderDishResponse fromOrderDishRespDto(OrderDishResponseDto orderDishResponseDto) {
        CustomOrderDishResponse customOrderDishResponse = new CustomOrderDishResponse();
        customOrderDishResponse.setDishName(orderDishResponseDto.getDishName());
        customOrderDishResponse.setDishTypeEnum(orderDishResponseDto.getDishTypeEnum());

        switch (orderDishResponseDto.getDishTypeEnum()) {
            case MEAT:
                customOrderDishResponse.setGrams(orderDishResponseDto.getGrams());
                break;
            case SOUP:
                customOrderDishResponse.setSoupAccompanimentEnum(orderDishResponseDto.getSoupAccompanimentEnum());
                break;
            case DESSERT:
                customOrderDishResponse.setDessertType(orderDishResponseDto.getDessertType());
                customOrderDishResponse.setFlavorTypeEnum(orderDishResponseDto.getFlavorTypeEnum());
                customOrderDishResponse.setToppingTypeEnum(orderDishResponseDto.getToppingTypeEnum());
                break;
            default:
                break;
        }

        return customOrderDishResponse;
    }


}
