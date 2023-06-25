package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.PinEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IOrderMessageEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IOrderMessageRepository;
import com.pragma.powerup.usermicroservice.domain.model.Pin;
import com.pragma.powerup.usermicroservice.domain.spi.IOrderMessagePersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderMessagePinMysqlAdapter implements IOrderMessagePersistencePort {

    private final IOrderMessageRepository orderMessageRepository;
    private final IOrderMessageEntityMapper orderMessageEntityMapper;

    @Override
    public void savePin(Pin pin) {
        orderMessageRepository.save(orderMessageEntityMapper.toEntity(pin));
    }

    @Override
    public Pin getPinByOrderId(Long orderId) {
        PinEntity pinEntity = orderMessageRepository.findByOrderId(orderId);
        return orderMessageEntityMapper.toDomain(pinEntity);
    }
}
