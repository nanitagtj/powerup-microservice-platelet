package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long id);
    boolean existsByName(String name);
    Page<Restaurant> getAllRestaurants(Pageable pageable);
    Long getRestaurantOwnerId(Long restaurantId);

}
