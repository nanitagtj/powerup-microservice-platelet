package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRestaurantRequestDto;
import com.pragma.powerup.usermicroservice.domain.model.EmployeeRestaurant;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeRestaurantRequestMapper {
    @Mapping(source = "restaurantId", target = "restaurant.id")
    EmployeeRestaurant toEmployeeRestaurant(EmployeeRestaurantRequestDto employeeRestaurantRequestDto);
    default Restaurant mapRestaurant(Long restaurantId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        return restaurant;
    }

}
