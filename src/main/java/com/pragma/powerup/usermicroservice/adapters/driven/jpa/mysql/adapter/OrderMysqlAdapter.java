package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import com.pragma.powerup.usermicroservice.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private List<Order> orders = new ArrayList<>();
    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        orderEntity = orderRepository.save(orderEntity);
        return orderEntityMapper.toDomain(orderEntity);
    }

    @Override
    public List<Order> getOrdersByClientId(Long clientId) {
        List<OrderEntity> orderEntities = orderRepository.findByClientId(clientId);
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    public Page<Order> getOrdersByStatusAndRestaurant(String status, Long restaurantId, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),pageable.getSort());
        Page<OrderEntity> orderEntities = orderRepository.findByStatusAndRestaurant(status, restaurantId, pageRequest);
        return orderEntityMapper.toDomainPage(orderEntities);
    }
}
