package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryRequestDto {
    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters are allowed")
    private String name;
}
