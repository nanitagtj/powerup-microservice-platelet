package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private String status;
    private String restaurant;
    private List<String> dishes;
    private LocalDateTime dateTime;
    private double amount;
}
