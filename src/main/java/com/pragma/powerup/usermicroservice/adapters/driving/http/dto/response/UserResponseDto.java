package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
