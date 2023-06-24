package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.naming.AuthenticationException;
import java.util.List;

public interface IOrderServicePort {
    void createOrder(Order order, HttpServletRequest request);
    List<Order> getRestaurantOrder(int pageNumber, int pageSize, String statusOrder, Long idEmployee);
    void assignEmployeeToOrder(Long orderId, Long employeeId);

    void updateStatusToReady(Long id, HttpServletRequest request);
}
