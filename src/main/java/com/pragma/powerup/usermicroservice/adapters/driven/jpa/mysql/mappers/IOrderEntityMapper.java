package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.usermicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {
    @Mapping(target = "restaurant", source = "order.restaurant")
    @Mapping(target = "dishQuantities", source = "order.dishQuantities")
    OrderEntity toEntity(Order order);
    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "dishQuantities", ignore = true)
    Order toDomain(OrderEntity orderEntity);

    List<Order> toDomainList(List<OrderEntity> orderEntities);

    default Page<Order> toDomainPage(Page<OrderEntity> orderEntities) {
        return orderEntities.map(this::toDomain);
    }


}
