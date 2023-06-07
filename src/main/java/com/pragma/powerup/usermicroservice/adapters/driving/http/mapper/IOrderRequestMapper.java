package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    @Mapping(source = "restaurantId", target = "restaurant.id")
    @Mapping(source = "dishesId", target = "dishes", qualifiedByName = "toDishList")
    Order toOrder(OrderRequestDto orderRequestDto);

    @Named("toDishList")
    default List<Dish> toDishList(List<Long> dishesId) {
        return dishesId.stream()
                .map(this::mapToDish)
                .collect(Collectors.toList());
    }

    default Dish mapToDish(Long dishId) {
        Dish dish = new Dish();
        dish.setId(dishId);
        return dish;
    }

    default List<Long> mapDishListToIds(List<Dish> dishes) {
        return dishes.stream()
                .map(Dish::getId)
                .collect(Collectors.toList());
    }
}
