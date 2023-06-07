package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.DishNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.DuplicateRestaurantName;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidUserException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClientPort userClientPort;

    private static final Long OWNER_ROLE_ID = 2L;

    @Autowired
    JwtProvider jwtProvider;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUserClientPort userClientPort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClientPort = userClientPort;
    }

    public void createDish(Dish dish, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(dish.getRestaurant().getId());
        dish.setActive(true);

        Long userId = jwtProvider.getUserIdFromToken(token);
        if (userId == null) {
            throw new InvalidUserException();
        }

        if (!restaurant.getIdOwner().equals(userId)) {
            throw new InvalidUserException();
        }
        validateUniqueName(dish.getName());
        dishPersistencePort.saveDish(dish);
    }

    private void validateUniqueName(String name) {
        if (dishPersistencePort.existsByName(name)) {
            throw new DuplicateRestaurantName();
        }
    }

    @Override
    public void updateDish(Long id, Dish dish, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long userId = jwtProvider.getUserIdFromToken(token);
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(dish.getRestaurant().getId());

        if (!restaurant.getIdOwner().equals(userId)) {
            throw new InvalidUserException();
        }

        Dish existingDish = dishPersistencePort.getDishById(id);

        existingDish.setPrice(dish.getPrice());
        existingDish.setDescription(dish.getDescription());

        dishPersistencePort.saveDish(existingDish);
    }

    public void updateDishStatus(Long id, boolean active, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long userId = jwtProvider.getUserIdFromToken(token);
        Dish existingDish = dishPersistencePort.getDishById(id);

        if (!existingDish.getRestaurant().getIdOwner().equals(userId)) {
            throw new InvalidUserException();
        }

        existingDish.setActive(active);
        dishPersistencePort.saveDish(existingDish);
    }

    @Override
    public Dish getDishById(Long dishId) {
        Dish dish = dishPersistencePort.getDishById(dishId);
        if (dish == null) {
            throw new DishNotFoundException();
        }
        return dish;
    }

    @Override
    public Page<Dish> getDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Pageable pageable) {
        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(restaurantId);

        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }

        Page<Dish> dishPage = dishPersistencePort.getDishesByRestaurantAndCategory(restaurantId, categoryId, pageable);

        List<Dish> activeDishes = new ArrayList<>();
        for (Dish dish : dishPage.getContent()) {
            if (dish.isActive()) {
                activeDishes.add(dish);
            }
        }

        return new PageImpl<>(activeDishes, pageable, dishPage.getTotalElements());
    }

    @Override
    public List<Dish> getDishesById(List<Long> ids) {
        List<Dish> dishes = dishPersistencePort.getDishesById(ids);
        if (dishes.isEmpty()) {
            throw new DishNotFoundException();
        }
        return dishes;
    }


}
