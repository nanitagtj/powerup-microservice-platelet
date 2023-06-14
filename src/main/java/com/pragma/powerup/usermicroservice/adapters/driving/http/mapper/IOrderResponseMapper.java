package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    @Mapping(target = "restaurant", expression = "java(mapRestaurant(createdOrder.getRestaurant()))")
    @Mapping(target = "dishes", expression = "java(mapDishes(convertDishQuantities(createdOrder.getDishQuantities())))")
    OrderResponseDto toOrderResponseDto(Order createdOrder);

    default String mapRestaurant(Restaurant restaurant) {
        return restaurant.getName();
    }

    default List<String> mapDishes(Map<DishEntity, Long> dishQuantities) {
        List<String> dishNames = new ArrayList<>();
        for (Map.Entry<DishEntity, Long> entry : dishQuantities.entrySet()) {
            DishEntity dishEntity = entry.getKey();
            Long quantity = entry.getValue();
            String dishNameWithQuantity = dishEntity.getName() + " (Quantity: " + quantity + ")";
            dishNames.add(dishNameWithQuantity);
        }
        return dishNames;
    }

    default Map<DishEntity, Long> convertDishQuantities(Map<Dish, Long> dishQuantities) {
        Map<DishEntity, Long> convertedMap = new HashMap<>();
        for (Map.Entry<Dish, Long> entry : dishQuantities.entrySet()) {
            DishEntity dishEntity = new DishEntity();
            dishEntity.setName(entry.getKey().getName());

            convertedMap.put(dishEntity, entry.getValue());
        }
        return convertedMap;
    }
}
