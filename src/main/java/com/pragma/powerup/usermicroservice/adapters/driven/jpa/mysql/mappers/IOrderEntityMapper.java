package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {

    @Mapping(target = "status", source = "status")
    @Mapping(target = "idRestaurant.id", source = "idRestaurant.id")
    OrderEntity toEntity(Order order);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "idRestaurant.id", source = "idRestaurant.id")
    List<OrderEntity> toEntity(List<Order> order);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "idRestaurant.id", source = "idRestaurant.id")
    Order toOrder(OrderEntity order);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "idRestaurant.id", source = "idRestaurant.id")
    List<Order> toOrder(Page<OrderEntity> order);

    List<Order> toDomainList(List<OrderEntity> orderEntities);

}
