package com.pragma.powerup.usermicroservice.adapters.driving.http.validator;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderDishRespDto;
import com.pragma.powerup.usermicroservice.domain.enums.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderDishRespDtoValidator implements ConstraintValidator<ValidOrderDishRespDto, OrderDishRespDto> {
    @Override
    public boolean isValid(OrderDishRespDto orderDishRespDto, ConstraintValidatorContext context) {
        DishTypeEnum dishTypeEnum = orderDishRespDto.getDishTypeEnum();

        if (dishTypeEnum == DishTypeEnum.MEAT) {
            // Validar los gramos para el tipo de plato "MEAT"
            int grams = orderDishRespDto.getGrams();
            return grams >= 250 && grams <= 750;
        } else if (dishTypeEnum == DishTypeEnum.SOUP) {
            // Validar el acompañante para el tipo de plato "SOUP"
            SoupAccompanimentEnum soupAccompanimentEnum = orderDishRespDto.getSoupAccompanimentEnum();
            return soupAccompanimentEnum != null;
        } else if (dishTypeEnum == DishTypeEnum.DESSERT) {
            // Validar el tipo de postre para el tipo de plato "DESSERT"
            DessertType dessertType = orderDishRespDto.getDessertType();
            return dessertType != null;
        } else if (dishTypeEnum == DishTypeEnum.DESSERT) {
            // Validar el sabor para el tipo de postre "ICE_CREAM"
            DessertType dessertType = orderDishRespDto.getDessertType();
            FlavorTypeEnum flavorTypeEnum = orderDishRespDto.getFlavorTypeEnum();
            return dessertType == DessertType.ICE_CREAM && flavorTypeEnum != null;
        } else if (dishTypeEnum == DishTypeEnum.DESSERT) {
            // Validar el topping para el tipo de postre "FLAN"
            DessertType dessertType = orderDishRespDto.getDessertType();
            ToppingTypeEnum toppingTypeEnum = orderDishRespDto.getToppingTypeEnum();
            return dessertType == DessertType.FLAN && toppingTypeEnum != null;
        }
        // Otros tipos de platos no requieren validación adicional
        return true;
    }
}
