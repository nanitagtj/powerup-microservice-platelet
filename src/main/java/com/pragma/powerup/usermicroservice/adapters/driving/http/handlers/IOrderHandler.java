package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.naming.AuthenticationException;
import java.util.List;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto, HttpServletRequest request);
    List<OrderResponseDto> getRestaurantOrders(int pageNumber, int pageSize, String statusOrder);
}
