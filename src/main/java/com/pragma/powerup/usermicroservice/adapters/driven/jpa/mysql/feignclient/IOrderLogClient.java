package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.feignclient;

import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-trazability", url = "localhost:8003/traceability")
public interface IOrderLogClient {
    @PostMapping("/orderLog")
    void saveOrderLog(@RequestBody String orderLogJson);
    @GetMapping("/orderLogs/{orderId}")
    List<OrderLogJson> getOrderLogsByOrderId(@PathVariable("orderId") Long orderId);
    @GetMapping("/orderElapsedTime/{orderId}")
    String calculateElapsedTimeByOrderId(@PathVariable("orderId") Long orderId);
}
