package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeAverageElapsedTimeDto {

    private Long employeeId;
    private String averageElapsedTime;

}
