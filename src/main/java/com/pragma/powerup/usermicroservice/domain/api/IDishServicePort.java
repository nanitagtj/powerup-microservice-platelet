package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Dish;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDishServicePort {

    void createDish(Dish dish, Long ownerId);
    void updateDishStatus(Long id, boolean active, HttpServletRequest request);
    void updateDish(Long id, Dish dish, Long ownerId);

    Dish getDishById(Long dishId);

    Page<Dish> getDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Pageable pageable);

    List<Dish> getDishesById(List<Long> ids);
}
