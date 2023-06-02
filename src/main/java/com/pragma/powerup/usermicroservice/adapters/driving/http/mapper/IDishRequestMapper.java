package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.usermicroservice.domain.model.Category;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {
    @Mapping(target = "category", source = "idCategory")
    @Mapping(target = "restaurant", source = "idRestaurant")
    Dish toDish (DishRequestDto dishRequestDto);
    @Mapping(target = "restaurant", source = "idRestaurant")
    Dish toDishUpdate (DishUpdateRequestDto dishUpdateRequestDto);
    default Category toCategory(Long idCategory) {
        return new Category(idCategory, null);
    }

    default Restaurant toRestaurant(Long idRestaurant) {
        return new Restaurant(idRestaurant, null, null, null, null, null, null);
    }
}
