package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
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

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.pragma.powerup.usermicroservice.configuration.Constants.ORDER_CREATED_MESSAGE;

@RestController
@RequestMapping("/platelet")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class OrderController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Add a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "order created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "order already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/order")
    public ResponseEntity<Map<String, Object>> createOrder(@Validated @RequestBody OrderRequestDto orderRequestDto, HttpServletRequest request) {
        OrderResponseDto orderResponseDto = orderHandler.createOrder(orderRequestDto, request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", ORDER_CREATED_MESSAGE);
        response.put("order", orderResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get orders by status and restaurant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/OrderResponseDto"))),
                    @ApiResponse(responseCode = "400", description = "Invalid status value",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByStatusAndRestaurant(@RequestParam String status, @RequestParam(required = false) Long restaurantId, Pageable pageable, HttpServletRequest request) throws AuthenticationException {
        Page<OrderResponseDto> orderPage = orderHandler.getOrdersByStatusAndRestaurant(status, restaurantId, pageable, request);
        return ResponseEntity.ok(orderPage);
    }

}
