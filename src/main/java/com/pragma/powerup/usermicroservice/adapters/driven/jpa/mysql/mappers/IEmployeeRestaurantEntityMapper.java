package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.EmployeeRestaurantEntity;
import com.pragma.powerup.usermicroservice.domain.model.EmployeeRestaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeRestaurantEntityMapper {
    @Mapping(source = "employeeId", target = "employeeId")
    @Mapping(source = "restaurant", target = "restaurant")
    EmployeeRestaurant toEmployeeRestaurant(EmployeeRestaurantEntity savedEntity);

    EmployeeRestaurantEntity toEntity(EmployeeRestaurant employeeRestaurant);
}
