package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.EmployeeAverageElapsedTimeDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderDishRespDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.validator.CustomOrderDishResponse;
import com.pragma.powerup.usermicroservice.domain.model.EmployeeRanking;
import com.pragma.powerup.usermicroservice.domain.model.Order;

import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    @Mapping(target = "idRestaurant", source = "idRestaurant.id")
    @IterableMapping(elementTargetType = OrderDish.class)
    List<OrderResponseDto> toOrderRes(List<Order> order);

    default Long map(Restaurant restaurant) {
        return restaurant.getId();
    }

    List<EmployeeAverageElapsedTimeDto> toDisplay(List<EmployeeRanking> employeeRankings);

    OrderResponseDto toOrderResponse(Order order);

    @Mapping(source = "grams", target = "grams")
    @Mapping(source = "dishTypeEnum", target = "dishTypeEnum")
    @Mapping(source = "soupAccompanimentEnum", target = "soupAccompanimentEnum")
    @Mapping(source = "dessertType", target = "dessertType")
    @Mapping(source = "flavorTypeEnum", target = "flavorTypeEnum")
    @Mapping(source = "toppingTypeEnum", target = "toppingTypeEnum")
    OrderDishResponseDto toOrderDishResponseDto(OrderDish orderDish);

    @Mapping(source = "dishTypeEnum", target = "dishTypeEnum")
    @Mapping(source = "grams", target = "grams")
    @Mapping(source = "soupAccompanimentEnum", target = "soupAccompanimentEnum")
    @Mapping(source = "dessertType", target = "dessertType")
    @Mapping(source = "flavorTypeEnum", target = "flavorTypeEnum")
    @Mapping(source = "toppingTypeEnum", target = "toppingTypeEnum")
    OrderDishResponseDto toOrderDishRespDto(CustomOrderDishResponse customOrderDishResponse);

}
