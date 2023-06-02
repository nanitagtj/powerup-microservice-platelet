package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.CategoryRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.CategoryResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.ICategoryHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryHandlerImpl implements ICategoryHandler {
    private final ICategoryResponseMapper categoryResponseMapper;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryServicePort categoryServicePort;

    @Override
    public void createCategory(CategoryRequestDto categoryRequestDto) {
        categoryServicePort.createCategory(categoryRequestMapper.toCategory(categoryRequestDto));
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryResponseMapper.toResponseList(categoryServicePort.getAllCategories());
    }
}
