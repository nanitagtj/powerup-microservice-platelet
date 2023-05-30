package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class RestaurantRequestDto {

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9 ]+$", message = "Invalid name")
    private String name;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{12}$", message = "The NIT must be numeric and contain exactly 12 digits")
    private String nit;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9]{12}$", message = "phone number must be like = +573221126845")
    private String phone;
    @Column(nullable = false)
    private String urlLogo;
    @Column(nullable = false)
    private Long idOwner;
}
