package com.pragma.powerup.usermicroservice.adapters.driving.http.validator;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderDishReqDto;
import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;
import com.pragma.powerup.usermicroservice.domain.enums.SoupAccompanimentEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SoupAccompanimentValidator implements ConstraintValidator<ValidSoupAccompaniment, OrderDishReqDto> {
    @Override
    public boolean isValid(OrderDishReqDto orderDishReqDto, ConstraintValidatorContext context) {
        if (orderDishReqDto.getDishTypeEnum() == DishTypeEnum.SOUP) {
            SoupAccompanimentEnum accompaniment = orderDishReqDto.getSoupAccompanimentEnum();
            return accompaniment != null;
        }
        return true;
    }
}
