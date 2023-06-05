package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController()
@RequestMapping("/platelet")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class DishController {
    private final IDishHandler dishHandler;

    @Operation(summary = "Add a new dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Dish already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/dish")
    public ResponseEntity<Map<String, String>> createDish(@Validated @RequestBody DishRequestDto dishRequestDto, HttpServletRequest request) {
        dishHandler.createDish(dishRequestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_CREATED_MESSAGE));
    }

    @Operation(summary = "Update dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Dish already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PatchMapping("/dishes/{id}")
    public ResponseEntity<?> updateDish(@PathVariable("id") Long id,@Validated @RequestBody DishUpdateRequestDto dishUpdateRequestDto, HttpServletRequest request) {
        dishHandler.updateDish(id, dishUpdateRequestDto, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_UPDATED_MESSAGE));
    }
    @Operation(summary = "Update status from dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Dish already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateDishStatus(@PathVariable("id") Long id, @RequestParam("active") boolean active, HttpServletRequest request) {
        dishHandler.updateDishStatus(id, active, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_UPDATED_STATUS_MESSAGE));
    }

    @Operation(summary = "Get all the dishes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All dishes returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DishResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/dishes")
    public ResponseEntity<Page<DishResponseDto>> getDishesByRestaurantAndCategory(@RequestParam("restaurantId") Long restaurantId, @RequestParam("categoryId") Long categoryId,
                                                              Pageable pageable) {
        Page<DishResponseDto> dishes = dishHandler.getDishesByRestaurantAndCategory(restaurantId, categoryId, pageable);
        return ResponseEntity.ok(dishes);
    }

}

