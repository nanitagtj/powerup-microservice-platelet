package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class CategoryResponseDto {

    private Long id;
    @NotNull
    private String name;
}
