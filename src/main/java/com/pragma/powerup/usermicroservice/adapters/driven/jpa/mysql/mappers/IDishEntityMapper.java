package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {
    @Mapping(target = "category", source = "category")
    @Mapping(target = "restaurant", source = "restaurant")
    DishEntity toEntity(Dish dish);

    Dish toDish(DishEntity dishEntity);

    default Page<Dish> toDomainPage(Page<DishEntity> entityPage) {
        List<Dish> dishes = entityPage.map(this::toDish).getContent();
        return new PageImpl<>(dishes, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
