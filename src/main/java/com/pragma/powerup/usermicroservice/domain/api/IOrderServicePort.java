package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.naming.AuthenticationException;

public interface IOrderServicePort {
    Order createOrder(Order order, HttpServletRequest req);
    Page<Order> getOrdersByStatusAndRestaurant(String status, Long restaurantId, Pageable pageable, HttpServletRequest request) throws AuthenticationException;

}
