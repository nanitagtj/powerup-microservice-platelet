package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;

import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException());
        return restaurantEntityMapper.restaurantEntityToRestaurant(restaurantEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return restaurantRepository.existsByName(name);
    }

    @Override
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        Sort sort = Sort.by(Sort.Order.asc("name"));
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<RestaurantEntity> restaurantEntityPage = restaurantRepository.findAll(sortedPageable);
        if (restaurantEntityPage.isEmpty()) {
            throw new NoDataFoundException();
        }
        return restaurantEntityMapper.toRestaurantPage(restaurantEntityPage);
    }

    @Override
    public Long getRestaurantOwnerId(Long restaurantId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoDataFoundException());

        return restaurantEntity.getIdOwner();
    }

    @Override
    public Restaurant getRestaurantByOwnerId(Long ownerId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findByIdOwner(ownerId);

        if (restaurantEntityOptional.isPresent()) {
            RestaurantEntity restaurantEntity = restaurantEntityOptional.get();
            return restaurantEntityMapper.restaurantEntityToRestaurant(restaurantEntity);
        } else {
            throw new NoDataFoundException();
        }
    }

}
