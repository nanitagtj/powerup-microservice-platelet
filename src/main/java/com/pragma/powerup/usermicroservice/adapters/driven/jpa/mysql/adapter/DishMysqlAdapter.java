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

import java.util.ArrayList;
import java.util.List;

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
    public List<Dish> getDishesById(List<Long> ids) {
        List<DishEntity> dishEntities = dishRepository.findByIdIn(ids);
        if (dishEntities.isEmpty()) {
            throw new NoDataFoundException();
        }
        List<Dish> dishes = new ArrayList<>();
        for (DishEntity dishEntity : dishEntities) {
            dishes.add(dishEntityMapper.toDish(dishEntity));
        }
        return dishes;
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

    @Override
    public Dish getDishById(Long dishId) {
        DishEntity dishEntity = dishRepository.findById(dishId)
                .orElseThrow(() -> new NoDataFoundException());

        return dishEntityMapper.toDish(dishEntity);
    }


}
