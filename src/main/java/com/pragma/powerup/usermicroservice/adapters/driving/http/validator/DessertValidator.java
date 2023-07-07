package com.pragma.powerup.usermicroservice.adapters.driving.http.validator;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderDishReqDto;
import com.pragma.powerup.usermicroservice.domain.enums.DessertType;
import com.pragma.powerup.usermicroservice.domain.enums.FlavorTypeEnum;
import com.pragma.powerup.usermicroservice.domain.enums.ToppingTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DessertValidator implements ConstraintValidator<ValidDessert, OrderDishReqDto> {
    @Override
    public boolean isValid(OrderDishReqDto orderDishReqDto, ConstraintValidatorContext context) {
        DessertType dessertType = orderDishReqDto.getDessertType();

        if (dessertType != null) {
            if (dessertType == DessertType.ICE_CREAM) {
                FlavorTypeEnum flavorTypeEnum = orderDishReqDto.getFlavorTypeEnum();
                return flavorTypeEnum != null;
            } else if (dessertType == DessertType.FLAN) {
                ToppingTypeEnum toppingTypeEnum = orderDishReqDto.getToppingTypeEnum();
                return toppingTypeEnum != null;
            }
        }

        return true;
    }
}
