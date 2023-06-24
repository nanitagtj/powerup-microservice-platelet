package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.feignclient.IUserFeignClient;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.clientapi.IUserClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor
public class UserClientAdapter implements IUserClientPort {

    private final IUserFeignClient userFeignClient;

    @Override
    public UserResponseDto getUserById(Long id, String header) {
        return userFeignClient.getUserById(id, header);
    }


}
