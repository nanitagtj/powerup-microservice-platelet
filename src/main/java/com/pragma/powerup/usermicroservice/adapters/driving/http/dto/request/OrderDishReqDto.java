package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDishReqDto {
    @Schema(title = "dishId", description = "id of a dish", example = "1")
    private Long dishId;
    @Schema(title = "quantity", description = "amount of the dish", example = "5")
    private int quantity;

}
