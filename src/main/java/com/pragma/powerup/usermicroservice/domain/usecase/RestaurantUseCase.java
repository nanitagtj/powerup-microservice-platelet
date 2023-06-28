package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.DuplicateRestaurantName;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidUserException;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.NullUserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClientPort userClientPort;
    private static final Long OWNER_ROLE_ID = 2L;


    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserClientPort userClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClientPort = userClientPort;
    }

    public void createRestaurant(Restaurant restaurant, String authorizationHeader) {
        UserResponseDto user = userClientPort.getUserById(restaurant.getIdOwner(), authorizationHeader);
        validateOwner(user);
        validateUniqueName(restaurant.getName());
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    private void validateOwner(UserResponseDto user) {
        if (user == null) {
            throw new NullUserException();
        }

        if (!user.getIdRole().equals(OWNER_ROLE_ID)) {
            throw new InvalidUserException();
        }
    }

    private void validateUniqueName(String name) {
        if (restaurantPersistencePort.existsByName(name)) {
            throw new DuplicateRestaurantName();
        }
    }
    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantPersistencePort.getRestaurantById(id);
    }

    @Override
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        Page<Restaurant> restaurantList =  restaurantPersistencePort.getAllRestaurants(pageable);
        return restaurantList;
    }

}
