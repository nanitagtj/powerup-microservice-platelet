package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEntityMapper {
    RestaurantEntity toEntity(Restaurant restaurant);

    Restaurant restaurantEntityToRestaurant(RestaurantEntity restaurantEntity);

    default Page<Restaurant> toRestaurantPage(Page<RestaurantEntity> restaurantEntityPage) {
        List<Restaurant> restaurantList = restaurantEntityPage.getContent()
                .stream()
                .map(this::restaurantEntityToRestaurant)
                .collect(Collectors.toList());
        return new PageImpl<>(restaurantList, Pageable.unpaged(), restaurantEntityPage.getTotalElements());
    }
}
