package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.*;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.validator.CustomOrderDishResponse;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.comparator.DishComparator;
import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pragma.powerup.usermicroservice.configuration.Constants.*;

@Validated
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

    @Operation(summary = "Add a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "order created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/OrderResponseDto"))),
                    @ApiResponse(responseCode = "409", description = "order already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping("/addOrder/{orderId}")
    public ResponseEntity<Map<String, OrderListResponseDto>> addOrder(@PathVariable Long orderId) {
        OrderListResponseDto responseDto = orderHandler.addOrder(orderId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, responseDto));
    }

    @Operation(summary = "Add multiple orders",
            responses = {
                    @ApiResponse(responseCode = "201", description = "orders added",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/OrderDish")))
            })
    @PostMapping("/addOrders")
    public ResponseEntity<Map<String, OrderListResponseDto>> addOrders(@RequestParam List<Long> orderIds) {
        OrderListResponseDto responseDto = orderHandler.addOrdersByIds(orderIds);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, responseDto));
    }
    @Operation(summary = "Take an order and get remaining dish types",
            responses = {
                    @ApiResponse(responseCode = "200", description = "order taken and remaining dish types retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", example = "[\"MEAT\", \"SOUP\", \"DESSERT\"]")))
            })
    @GetMapping("/takeOrder")
    public ResponseEntity<List<DishTypeEnum>> takeOrder() {
        List<DishTypeEnum> remainingDishTypes = orderHandler.takeOrder();
        return ResponseEntity.ok(remainingDishTypes);
    }
    @Operation(summary = "Get pending orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of pending orders",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = CustomOrderDishResponse.class)))
            })
    @GetMapping("/pendingOrders")
    public ResponseEntity<List<CustomOrderDishResponse>> getPendingOrders() {
        List<OrderDishResponseDto> pendingOrders = orderHandler.getPendingOrders();
        List<CustomOrderDishResponse> customResponses = new ArrayList<>();
        for (OrderDishResponseDto orderDishResponseDto : pendingOrders) {
            CustomOrderDishResponse customResponse = CustomOrderDishResponse.fromOrderDishRespDto(orderDishResponseDto);
            customResponses.add(customResponse);
        }

        return ResponseEntity.ok(customResponses);
    }

    private List<CustomOrderDishResponse> formatPendingOrders(List<OrderDish> pendingOrders) {
        List<CustomOrderDishResponse> formattedOrders = new ArrayList<>();

        for (OrderDish orderDish : pendingOrders) {
            CustomOrderDishResponse formattedOrder = new CustomOrderDishResponse();
            // Lógica para configurar los atributos personalizados en la clase CustomOrderDishResponse
            formattedOrders.add(formattedOrder);
        }

        return formattedOrders;
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
    @PutMapping("/order/assign")
    public ResponseEntity<Map<String, String>> assignEmployeeToOrder(@RequestParam List<Long> orderIds, HttpServletRequest request) {
        orderHandler.assignEmployeeToOrder(orderIds, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, ORDER_UPDATED_MESSAGE));
    }

    @Operation(summary = "Update order to ready",
            responses = {
                    @ApiResponse(responseCode = "200", description = ORDER_UPDATE_MESSAGE,
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Message"))),
                    @ApiResponse(responseCode = "404", description = ORDER_NOT_FOUND_EXCEPTION,
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS_MESSAGE,
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))

            })
    @PutMapping("status-ready/{id}")
    public ResponseEntity<Map<String,String>> updateStatusToReady(@PathVariable Long id, HttpServletRequest request) {
        orderHandler.updateStatusToReady(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, ORDER_UPDATE_MESSAGE));
    }

    @Operation(summary = "Update order status to delivered",
            responses = {
                    @ApiResponse(responseCode = "200", description = "order status updated to delivered",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Message"))),
                    @ApiResponse(responseCode = "404", description = ORDER_NOT_FOUND_EXCEPTION,
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = INVALID_PIN_EXCEPTION,
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "401", description = WRONG_CREDENTIALS_MESSAGE,
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))

            })
    @PutMapping("order/{orderId}/status-delivered")
    public ResponseEntity<Map<String, String>> updateOrderStatusToDelivered(@PathVariable Long orderId, @RequestParam String pin) {
        orderHandler.updateStatusToDelivered(orderId, pin);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, ORDER_STATUS_DELIVERED_MESSAGE));
    }

    @Operation(summary = "Cancel an order",
            responses = {
                    @ApiResponse(responseCode = "204", description = "order canceled"),
                    @ApiResponse(responseCode = "403", description = "unable to cancel the order"),
                    @ApiResponse(responseCode = "404", description = "order not found")
            })
    @DeleteMapping("/cancel/order/{orderId}")
    public ResponseEntity<Map<String,String>> cancelOrder(@PathVariable Long orderId, HttpServletRequest request) {
        orderHandler.cancelOrder(orderId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, CANCELED_ORDER_MESSAGE));
    }
    @Operation(summary = "Get logs of an order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Logs retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/OrderLogJson"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/order/{orderId}/logs")
    public ResponseEntity<List<OrderLogJson>> getOrderLogs(@PathVariable Long orderId, HttpServletRequest request) {
        List<OrderLogJson> logs = orderHandler.getOrderLogsByOrderId(orderId, request);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Calculate elapsed time for an order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Elapsed time calculated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ElapsedTimeResponseDto"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/order/{orderId}/elapsed-time")
    public ResponseEntity<Map<String, ElapsedTimeResponseDto>> calculateElapsedTime(@PathVariable Long orderId, HttpServletRequest request) {
        String elapsedTime = orderHandler.calculateElapsedTime(orderId, request);
        ElapsedTimeResponseDto responseDto = new ElapsedTimeResponseDto(elapsedTime);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.EFFICIENCY_MESSAGE, responseDto));
    }

    @Operation(summary = "Calculate average elapsed time for an employee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Average elapsed time calculated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/AverageElapsedTimeResponseDto"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/employee/{assignedEmployeeId}/average-elapsed-time")
    public ResponseEntity<Map<String, AverageElapsedTimeResponseDto>> calculateAverageElapsedTimeByEmployee(@PathVariable("assignedEmployeeId") Long assignedEmployeeId, HttpServletRequest request) {
        String elapsedTime = orderHandler.calculateAverageElapsedTimeByEmployee(assignedEmployeeId, request);
        AverageElapsedTimeResponseDto responseDto = new AverageElapsedTimeResponseDto(elapsedTime);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.EFFICIENCY_BY_EMPLOYEE_MESSAGE, responseDto));
    }

    @Operation(summary = "Get average elapsed time ranking",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Average elapsed time ranking retrieved",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/AverageElapsedTimeRankingResponseDto"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/average-elapsed-time-ranking")
    public ResponseEntity<AverageElapsedTimeRankingResponseDto> displayAverageElapsedTimeRanking(HttpServletRequest request) {
        List<EmployeeAverageElapsedTimeDto> ranking = orderHandler.displayEmployeeRanking(request);
        AverageElapsedTimeRankingResponseDto responseDto = new AverageElapsedTimeRankingResponseDto(ranking);
        return ResponseEntity.ok(responseDto);
    }
}
