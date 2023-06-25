package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.PinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrderMessageRepository extends JpaRepository<PinEntity, Long> {
    @Query("SELECT p FROM PinEntity p WHERE p.orderId.id = :orderId")
    PinEntity findByOrderId(Long orderId);
}
