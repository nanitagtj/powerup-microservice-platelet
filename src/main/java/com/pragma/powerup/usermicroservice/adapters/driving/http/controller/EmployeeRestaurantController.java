package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.EmployeeRestaurantResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IEmployeeRestaurantHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController()
@RequestMapping("/platelet")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class EmployeeRestaurantController {

    private final IEmployeeRestaurantHandler employeeRestaurantHandler;

    @Operation(summary = "Assign employee to restaurant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee assigned to restaurant",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = EmployeeRestaurantResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Employee or restaurant not found",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Error.class)))})
    @PostMapping("/employee/assign-restaurant")
    public ResponseEntity<Map<String, String>> assignEmployeeToRestaurant(@Validated @RequestBody EmployeeRestaurantRequestDto employeeRestaurantRequestDto, HttpServletRequest request) {
        employeeRestaurantHandler.assignEmployeeToRestaurant(employeeRestaurantRequestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.EMPLOYEERESTAURANT_CREATED_MESSAGE));
    }
}
