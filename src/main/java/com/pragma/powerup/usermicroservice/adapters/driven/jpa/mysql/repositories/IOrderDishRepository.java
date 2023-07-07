package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity,Long> {
    List<OrderDishEntity> findByOrder_IdRestaurant_IdAndOrder_Status(Long idRestaurant, String status);

    OrderDishEntity findOrderDishByOrderId(Long orderId);

    List<OrderDishEntity> findByOrder_IdAndDishTypeEnum(Long orderId, DishTypeEnum dishTypeEnum);
}
