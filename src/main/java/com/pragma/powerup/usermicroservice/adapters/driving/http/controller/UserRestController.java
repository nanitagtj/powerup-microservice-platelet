package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler personHandler;

    @Operation(summary = "Add a new user",
            responses = {
                @ApiResponse(responseCode = "201", description = "Person created",
                        content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                @ApiResponse(responseCode = "409", description = "Person already exists",
                        content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping
    public ResponseEntity<Map<String, String>> savePerson(@RequestBody UserRequestDto userRequestDto) {
        personHandler.saveUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.PERSON_CREATED_MESSAGE));
    }
}
