package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.PinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderMessageRepository extends JpaRepository<PinEntity, Long> {

}
