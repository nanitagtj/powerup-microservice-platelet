package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.feignclient;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "powerup-microservice-messenger", url = "localhost:8090/message")
public interface IMessageClient {

        @Headers("Content-Type: application/json")
        @PostMapping("/pin")
        void sendPinSms(@RequestBody String messengerSms);

}
