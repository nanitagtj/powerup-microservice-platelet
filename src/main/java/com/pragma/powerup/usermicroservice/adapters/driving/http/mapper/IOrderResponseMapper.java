package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.Order;

import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    @IterableMapping(elementTargetType = OrderDish.class)
    List<OrderResponseDto> toOrderRes(List<Order> order);

    default Long map(Restaurant restaurant) {
        return restaurant.getId();
    }
}
