package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public boolean existsByName(String name) {
        return dishRepository.existsByName(name);
    }

    @Override
    public Dish getDishById(Long id) {
        DishEntity dishEntity = dishRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException());
        return dishEntityMapper.toDish(dishEntity);
    }

    @Override
    public Page<Dish> getDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Pageable pageable) {
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantId);
        CategoryEntity category = new CategoryEntity();
        category.setId(categoryId);
        Page<DishEntity> dishEntityPage = dishRepository.getDishesByRestaurantAndCategory(restaurant, category, pageable);
        return dishEntityMapper.toDomainPage(dishEntityPage);
    }

}
