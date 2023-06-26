package com.pragma.powerup.usermicroservice.domain.clientapi;

import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;

import java.util.List;

public interface IOrderLogClientPort {

    void saveOrderLog(String orderLogJson);

    List<OrderLogJson> getOrderLogsByOrderId(Long orderId);
}
