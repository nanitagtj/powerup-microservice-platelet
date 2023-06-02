package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.CategoryRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.CategoryResponseDto;

import java.util.List;

public interface ICategoryHandler {
    void createCategory(CategoryRequestDto categoryRequestDto);
    List<CategoryResponseDto> getAllCategories();
}
