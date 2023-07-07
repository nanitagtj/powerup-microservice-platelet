package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import com.pragma.powerup.usermicroservice.domain.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_dish")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private DishEntity dish;

    @Column(nullable = false)
    private int quantity;
    @Enumerated(EnumType.STRING)
    private DishTypeEnum dishTypeEnum;

    private Integer grams;
    @Enumerated(EnumType.STRING)
    private SoupAccompanimentEnum soupAccompanimentEnum;
    @Enumerated(EnumType.STRING)
    private DessertType dessertType;
    @Enumerated(EnumType.STRING)
    private FlavorTypeEnum flavorTypeEnum;
    @Enumerated(EnumType.STRING)
    private ToppingTypeEnum toppingTypeEnum;
}
