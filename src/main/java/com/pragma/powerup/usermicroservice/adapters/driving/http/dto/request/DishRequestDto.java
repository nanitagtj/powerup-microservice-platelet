package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.persistence.Column;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DishRequestDto {

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
    @Column(nullable = false)
    private Long idCategory;
    @Column(nullable = false)
    private Long idRestaurant;
    private boolean active;
}
