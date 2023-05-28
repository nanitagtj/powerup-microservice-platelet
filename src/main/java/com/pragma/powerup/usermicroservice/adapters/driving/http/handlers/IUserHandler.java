package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;

public interface IUserHandler {
    void saveUser(UserRequestDto personRequestDto);
}
