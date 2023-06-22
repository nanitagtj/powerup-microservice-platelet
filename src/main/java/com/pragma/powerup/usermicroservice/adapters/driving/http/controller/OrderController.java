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
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.usermicroservice.configuration.Constants.*;

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
    public ResponseEntity<Map<String, String>> saveOrder(@RequestBody OrderRequestDto orderRequestDto, HttpServletRequest request) {
        orderHandler.createOrder(orderRequestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, ORDER_CREATED_MESSAGE));
    }

    @Operation(summary = "Get orders from a restaurant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "[{}]",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Message"))),
                    @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS_MESSAGE,
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Message"))),

            })
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String statusOrder) {
        return ResponseEntity.ok(orderHandler.getRestaurantOrders(pageNumber, pageSize, statusOrder));
    }

    @Operation(summary = "Assign an employee to an order",
            responses = {
                    @ApiResponse(responseCode = "204", description = "employee assigned"),
                    @ApiResponse(responseCode = "404", description = "order not found"),
                    @ApiResponse(responseCode = "403", description = "employee not allowed to assign to the order")
            })
    @PutMapping("/order/{orderId}/assign")
    public ResponseEntity<Map<String, String>> assignEmployeeToOrder(@PathVariable Long orderId, HttpServletRequest request) {
        orderHandler.assignEmployeeToOrder(orderId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, ORDER_UPDATED_MESSAGE));
    }

}
