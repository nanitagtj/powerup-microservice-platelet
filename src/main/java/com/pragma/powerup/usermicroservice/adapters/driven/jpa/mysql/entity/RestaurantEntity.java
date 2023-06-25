package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9 ]+$", message = "Invalid name")
    private String name;
    @Column(nullable = false)
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

    public RestaurantEntity(Long id) {
        this.id = id;
    }
}
