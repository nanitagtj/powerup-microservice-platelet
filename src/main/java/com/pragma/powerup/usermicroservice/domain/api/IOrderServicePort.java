package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Order;
import jakarta.servlet.http.HttpServletRequest;

public interface IOrderServicePort {
    Order createOrder(Order order, HttpServletRequest req);

}
