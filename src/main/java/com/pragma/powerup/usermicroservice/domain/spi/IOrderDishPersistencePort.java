package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;

import java.util.List;

public interface IOrderDishPersistencePort {
    void saveOrderDish(List<OrderDish> orderDishes);
    List<OrderDish> getRestaurantOrderDish(String status, Long idRestaurant);
    DishTypeEnum getDishTypeByOrderId(Long orderId);
}
