package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Dish;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishServicePort {

    void createDish(Dish dish, HttpServletRequest req);
    void updateDishStatus(Long id, boolean active, HttpServletRequest request);
    void updateDish(Long id, Dish dish, HttpServletRequest request);
    Page<Dish> getDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Pageable pageable);

}
