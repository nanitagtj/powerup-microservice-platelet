package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = true)
    private Long assignedEmployeeId;

    @ManyToOne
    @JoinColumn(name = "id_restaurant", referencedColumnName = "id")
    private RestaurantEntity idRestaurant;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private double amount;
}
