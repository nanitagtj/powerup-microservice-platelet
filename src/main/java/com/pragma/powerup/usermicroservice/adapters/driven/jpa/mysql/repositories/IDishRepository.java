package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    boolean existsByName(String name);
    Page<DishEntity> getDishesByRestaurantAndCategory(RestaurantEntity restaurant, CategoryEntity category, Pageable pageable);

    List<DishEntity> findByIdIn(List<Long> ids);

}
