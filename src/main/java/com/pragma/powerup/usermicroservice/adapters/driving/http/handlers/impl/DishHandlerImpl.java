package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IDishResponseMapper;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
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
    private final JwtProvider jwtProvider;
    @Override
    public void createDish(DishRequestDto dishRequestDto, HttpServletRequest request) {
        Long ownerId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        dishServicePort.createDish(dishRequestMapper.toDish(dishRequestDto), ownerId);
    }

    @Override
    public void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto, HttpServletRequest request) {
        Long ownerId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        dishServicePort.updateDish(id, dishRequestMapper.toDishUpdate(dishUpdateRequestDto), ownerId);
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
