package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.feignclient;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-microservice", url = "localhost:8001/user")
public interface IUserFeignClient {
    @GetMapping("/find/{id}")
    UserResponseDto getUserById(@PathVariable("id") Long id, @RequestHeader("Authorization") String header);

}
