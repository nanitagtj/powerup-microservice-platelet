package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Pin;

public interface IOrderMessagePersistencePort {

    void savePin(Pin pin);
}
