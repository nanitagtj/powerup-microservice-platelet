package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RestaurantDto {

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9 ]+$", message = "Invalid name")
    private String name;
    @Column(nullable = false)
    private String urlLogo;

}
