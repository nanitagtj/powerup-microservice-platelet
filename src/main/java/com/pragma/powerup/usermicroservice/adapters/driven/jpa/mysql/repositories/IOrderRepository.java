package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByClientId(Long clientId);

    @Query("SELECT o FROM OrderEntity o WHERE o.status = :status AND o.idRestaurant.id = :restaurantId")
    Page<OrderEntity> findByIdRestaurant(PageRequest pageable, @Param("status") String status, @Param("restaurantId") Long idRestaurant);

    List<OrderEntity> findByAssignedEmployeeId(Long assignedEmployeeId);
}
