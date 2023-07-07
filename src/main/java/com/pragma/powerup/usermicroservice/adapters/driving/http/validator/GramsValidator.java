package com.pragma.powerup.usermicroservice.adapters.driving.http.validator;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderDishReqDto;
import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GramsValidator implements ConstraintValidator<ValidGrams, OrderDishReqDto> {
    @Override
    public boolean isValid(OrderDishReqDto orderDish, ConstraintValidatorContext context) {
        if (orderDish.getDishTypeEnum() == DishTypeEnum.MEAT) {
            Integer grams = orderDish.getGrams();
            return grams != null && grams >= 250 && grams <= 750;
        }
        return true;
    }
}
