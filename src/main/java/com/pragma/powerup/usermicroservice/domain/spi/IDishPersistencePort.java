package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    boolean existsByName(String name);

    Dish getDishById(Long id);
    Page<Dish> getDishesByRestaurantAndCategory(Long restaurantId, Long category, Pageable pageable);

}
