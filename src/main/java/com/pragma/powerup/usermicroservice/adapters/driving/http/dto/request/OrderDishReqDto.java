package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.usermicroservice.domain.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDishReqDto {
    @Schema(title = "dishId", description = "id of a dish", example = "1")
    private Long dishId;
    @Schema(title = "quantity", description = "amount of the dish", example = "5")
    private int quantity;
    private DishTypeEnum dishTypeEnum;
    @Min(250)
    @Max(750)
    private int grams;
    private SoupAccompanimentEnum soupAccompanimentEnum;
    private DessertType dessertType;
    private FlavorTypeEnum flavorTypeEnum;
    private ToppingTypeEnum toppingTypeEnum;

}
