package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {
    @Mapping(target = "idRestaurant", source = "restaurant.id")
    @Mapping(target = "idCategory", source = "category.id")
    DishResponseDto dishResponseToDish(Dish dish);

    default Page<DishResponseDto> toDishResponsePage(Page<Dish> dishPage) {
        List<DishResponseDto> dishResponseDto = dishPage.getContent()
                .stream()
                .map(this::dishResponseToDish)
                .collect(Collectors.toList());

        return new PageImpl<>(dishResponseDto, dishPage.getPageable(), dishPage.getTotalElements());
    }
}
