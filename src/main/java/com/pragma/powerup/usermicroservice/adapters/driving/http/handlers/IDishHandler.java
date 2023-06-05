package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishHandler {
    void createDish(DishRequestDto dishRequestDto, HttpServletRequest request);
    void updateDish(Long id, DishUpdateRequestDto dishUpdateRequestDto, HttpServletRequest request);
    void updateDishStatus(Long id, boolean active, HttpServletRequest request);
    Page<DishResponseDto> getDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Pageable pageable);
}
