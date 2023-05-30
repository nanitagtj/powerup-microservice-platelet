package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
public class UserResponseDto {

    private Long id;

    private String dniNumber;

    private String mail;

    private String name;

    private String phone;

    private String surname;

    private Long idRole;

    private LocalDate birthDate;
}
