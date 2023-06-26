package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.feignclient.IOrderLogClient;
import com.pragma.powerup.usermicroservice.domain.clientapi.IOrderLogClientPort;
import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderLogClientAdapter implements IOrderLogClientPort {
    private final IOrderLogClient orderLogClient;

    @Override
    public void saveOrderLog(String orderLogJson) {
        orderLogClient.saveOrderLog(orderLogJson);
    }

    @Override
    public List<OrderLogJson> getOrderLogsByOrderId(Long orderId) {
        return orderLogClient.getOrderLogsByOrderId(orderId);
    }
}
