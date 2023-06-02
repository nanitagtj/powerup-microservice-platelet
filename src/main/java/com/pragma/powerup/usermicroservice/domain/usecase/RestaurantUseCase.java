package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import com.pragma.powerup.usermicroservice.domain.exceptions.DuplicateRestaurantName;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidUserException;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import jakarta.servlet.http.HttpServletRequest;
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

    public void createRestaurant(Restaurant restaurant, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        UserResponseDto user = userClientPort.getUserById(restaurant.getIdOwner(), token);
        validateOwner(user, token);
        validateUniqueName(restaurant.getName());
        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    private void validateOwner(UserResponseDto user, String token) {
        if (user == null) {
            throw new InvalidUserException();
        }

        Long userRoleId = user.getIdRole();
        UserResponseDto roleUser = userClientPort.getUserById(userRoleId, token);

        if (roleUser == null || !OWNER_ROLE_ID.equals(roleUser.getId())) {
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
