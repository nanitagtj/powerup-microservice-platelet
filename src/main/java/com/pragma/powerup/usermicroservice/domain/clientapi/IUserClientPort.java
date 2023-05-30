package com.pragma.powerup.usermicroservice.domain.clientapi;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;

public interface IUserClientPort {
    UserResponseDto getUserById(Long id, String header);
}
