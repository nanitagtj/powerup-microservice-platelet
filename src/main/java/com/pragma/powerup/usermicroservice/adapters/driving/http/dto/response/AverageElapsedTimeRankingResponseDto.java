package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AverageElapsedTimeRankingResponseDto {

    private List<EmployeeAverageElapsedTimeDto> ranking;
}


