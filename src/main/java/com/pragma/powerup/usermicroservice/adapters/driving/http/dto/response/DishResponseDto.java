package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DishResponseDto {
    private Long id;
    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private Long idCategory;
    private Long idRestaurant;
    private boolean active;

}
