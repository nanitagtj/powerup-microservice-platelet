package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
    public class OrderMysqlAdapter implements IOrderPersistencePort {
        private final IOrderRepository orderRepository;
        private final IOrderEntityMapper orderEntityMapper;

    @Override
    public Order saveOrder(Order order) {
        return orderEntityMapper.toOrder(orderRepository.saveAndFlush(orderEntityMapper.toEntity(order)));
    }

    @Override
    public List<Order> getOrdersByClientId(Long clientId) {
        List<OrderEntity> orderEntities = orderRepository.findByClientId(clientId);
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    public List<Order> getRestaurantOrder(int pageNumber, int pageSize, String status, Long restaurantId) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        return orderEntityMapper.toOrder(orderRepository.findByIdRestaurant(pageable, status, restaurantId));
    }

    @Override
    public Order getOrderById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
        if (orderEntity != null) {
            return orderEntityMapper.toOrder(orderEntity);
        }
        return null;
    }

}
