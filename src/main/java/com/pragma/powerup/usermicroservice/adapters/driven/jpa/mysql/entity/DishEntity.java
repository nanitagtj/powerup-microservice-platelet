package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9 ]+$", message = "Invalid name")
    private String name;
    @Column(nullable = false)
    @Positive(message = "Price must be a positive number")
    private int price;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;
    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurant;
    private boolean active;

}
