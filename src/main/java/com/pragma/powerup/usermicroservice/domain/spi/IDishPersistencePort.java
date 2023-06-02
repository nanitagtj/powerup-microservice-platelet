package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    boolean existsByName(String name);

    Dish getDishById(Long id);

}
