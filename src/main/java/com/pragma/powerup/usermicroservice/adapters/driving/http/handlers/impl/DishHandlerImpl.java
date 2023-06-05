package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IDishResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {
    private final IDishRequestMapper dishRequestMapper;
    private final IDishServicePort dishServicePort;
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public void createDish(DishRequestDto dishRequestDto, HttpServletRequest request) {
        dishServicePort.createDish(dishRequestMapper.toDish(dishRequestDto), request);
    }

    @Override
    public void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto, HttpServletRequest request) {
        dishServicePort.updateDish(id, dishRequestMapper.toDishUpdate(dishUpdateRequestDto), request);
    }

    @Override
    public void updateDishStatus(Long id, boolean active, HttpServletRequest request) {
        dishServicePort.updateDishStatus(id, active, request);
    }

    @Override
    public Page<DishResponseDto> getDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Pageable pageable) {
        Page<Dish> dishes = dishServicePort.getDishesByRestaurantAndCategory(restaurantId, categoryId, pageable);
        return dishResponseMapper.toDishResponsePage(dishes);
    }

}
