package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    boolean existsByName(String name);

    List<Dish> getDishesById(List<Long> ids);

    Page<Dish> getDishesByRestaurantAndCategory(Long restaurantId, Long category, Pageable pageable);

    Dish getDishById(Long dishId);
}
