package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);
    List<Order> getOrdersByClientId(Long clientId);
    List<Order> getRestaurantOrder(int pageNumber, int pageSize, String status, Long restaurantId);

}
