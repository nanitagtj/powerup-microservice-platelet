package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderDishReqDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    @Mapping(target = "idRestaurant.id", source = "idRestaurant")
    @IterableMapping(elementTargetType = OrderDish.class)
    Order toOrder(OrderRequestDto orderReqDto);

    @Mapping(target = "dish.id", source = "dishId")
    OrderDish orderDishReqDtoToOrderDish(OrderDishReqDto orderDishReqDto);

    default Dish map(Long value) {
        Dish dish = new Dish();
        dish.setId(value);
        return dish;
    }
}
