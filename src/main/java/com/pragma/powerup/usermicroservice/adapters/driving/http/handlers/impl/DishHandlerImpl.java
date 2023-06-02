package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {
    private final IDishRequestMapper dishRequestMapper;
    private final IDishServicePort dishServicePort;

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
}
