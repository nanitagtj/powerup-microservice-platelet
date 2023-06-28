package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.EmployeeAverageElapsedTimeDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOrderRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderServicePort orderServicePort;
    private final IOrderResponseMapper orderResponseMapper;
    private final HttpServletRequest request;
    private final JwtProvider jwtProvider;


    @Override
    public void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request) {
        Long clientId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        orderServicePort.createOrder(orderRequestMapper.toOrder(orderRequestDto), clientId);
    }

    @Override
    public List<OrderResponseDto> getRestaurantOrders(int pageNumber, int pageSize, String statusOrder) {
        return orderResponseMapper.toOrderRes(orderServicePort.getRestaurantOrder(pageNumber, pageSize, statusOrder, jwtProvider.getUserIdFromToken(request.getHeader("Authorization"))));
    }

    @Override
    public void assignEmployeeToOrder(List<Long> orderIds, HttpServletRequest request) {
        Long employeeId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        orderServicePort.assignEmployeeToOrders(orderIds, employeeId);
    }

    @Override
    public void updateStatusToReady(Long id, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        orderServicePort.updateStatusToReady(id, authorizationHeader);
    }

    @Override
    public void updateStatusToDelivered(Long orderId,String pin) {
        orderServicePort.updateStatusToDelivered(orderId, pin);
    }

    @Override
    public void cancelOrder(Long orderId, HttpServletRequest request) {
        Long clientId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        orderServicePort.cancelOrder(orderId, clientId);
    }

    @Override
    public List<OrderLogJson> getOrderLogsByOrderId(Long orderId, HttpServletRequest request) {
        Long clientId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        return orderServicePort.getOrderLogsByOrderId(orderId, clientId);
    }

    @Override
    public String calculateElapsedTime(Long orderId, HttpServletRequest request) {
        Long ownerId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        String elapsedTime = orderServicePort.calculateElapsedTime(orderId, ownerId);
        return formatElapsedTime(elapsedTime);
    }

    @Override
    public String calculateAverageElapsedTimeByEmployee(Long assignedEmployeeId, HttpServletRequest request) {
        Long ownerId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        String averageElapsedTime = orderServicePort.calculateAverageElapsedTimeByEmployee(assignedEmployeeId, ownerId);
        return formatElapsedTime(averageElapsedTime);
    }

    @Override
    public List<EmployeeAverageElapsedTimeDto> displayEmployeeRanking(HttpServletRequest request) {
        Long ownerId = jwtProvider.getUserIdFromToken(request.getHeader("Authorization"));
        List<EmployeeAverageElapsedTimeDto> averageElapsedTimeList = orderResponseMapper.toDisplay(orderServicePort.displayEmployeeRanking(ownerId));

        formatElapsedTime(averageElapsedTimeList);

        return averageElapsedTimeList;
    }

    private void formatElapsedTime(List<EmployeeAverageElapsedTimeDto> employeeList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (EmployeeAverageElapsedTimeDto employee : employeeList) {
            String averageElapsedTimeStr = employee.getAverageElapsedTime();
            Duration averageElapsedTime = Duration.parse(averageElapsedTimeStr);
            LocalTime localTime = LocalTime.MIDNIGHT.plus(averageElapsedTime);
            String formattedElapsedTime = localTime.format(formatter);
            employee.setAverageElapsedTime(formattedElapsedTime);
        }
    }

    private String formatElapsedTime(String elapsedTime) {
        Duration duration = Duration.parse(elapsedTime);
        LocalTime localTime = LocalTime.MIDNIGHT.plus(duration);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return localTime.format(formatter);
    }
}
