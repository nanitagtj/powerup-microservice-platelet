package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Order;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);
}
