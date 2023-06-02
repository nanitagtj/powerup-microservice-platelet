package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.DuplicateRestaurantName;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidUserException;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClientPort userClientPort;

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

}
