package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import org.mapstruct.*;

import java.util.Map;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    @Mapping(source = "restaurantId", target = "restaurant.id")
    @Mapping(source = "dishQuantities", target = "dishQuantities")
    Order toOrder(OrderRequestDto orderRequestDto);

    @IterableMapping(qualifiedByName = "toDishQuantitiesMap")
    Map<Dish, Long> toDishQuantitiesMap(Map<Long, Long> dishQuantities);

    default Dish mapToDish(Long dishId) {
        Dish dish = new Dish();
        dish.setId(dishId);
        return dish;
    }

}
