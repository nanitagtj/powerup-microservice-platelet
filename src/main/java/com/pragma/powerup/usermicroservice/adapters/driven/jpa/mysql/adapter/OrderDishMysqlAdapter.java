package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.pragma.powerup.usermicroservice.domain.enums.DishTypeEnum;
import com.pragma.powerup.usermicroservice.domain.model.OrderDish;
import com.pragma.powerup.usermicroservice.domain.spi.IOrderDishPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDishMysqlAdapter implements IOrderDishPersistencePort {

    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public void saveOrderDish(List<OrderDish> orderDishes) {
        orderDishRepository.saveAll(orderDishEntityMapper.toEntity(orderDishes));
    }

    @Override
    public List<OrderDish> getRestaurantOrderDish(String status, Long idRestaurant) {
        return orderDishEntityMapper.toModel(orderDishRepository.findByOrder_IdRestaurant_IdAndOrder_Status(idRestaurant, status));
    }

    @Override
    public DishTypeEnum getDishTypeByOrderId(Long orderId) {
        return orderDishRepository.findOrderDishByOrderId(orderId).getDishTypeEnum();
    }

}
