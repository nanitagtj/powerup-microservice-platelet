package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DishUpdateRequestDto {

    @Column(nullable = false)
    @Positive(message = "Price must be a positive number")
    private int price;
    @NotBlank(message = "should not be empty")
    private String description;
    @Column(nullable = false)
    private Long idRestaurant;
}
