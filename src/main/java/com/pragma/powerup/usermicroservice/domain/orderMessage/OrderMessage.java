package com.pragma.powerup.usermicroservice.domain.orderMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.usermicroservice.domain.exceptions.OrderNoFoundException;
import com.pragma.powerup.usermicroservice.domain.model.MessengerSms;
import com.pragma.powerup.usermicroservice.domain.model.Order;

import java.util.Objects;

public class OrderMessage {

    public static void validateOrder(Order order, Long id){
        if(order==null){
            throw new OrderNoFoundException(id);
        }
    }

    public static String createJson(Order order, String phone, String pin) {
        MessengerSms messengerSms = new MessengerSms(phone, order.getIdRestaurant().getPhone(), pin);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(messengerSms);
        } catch (JsonProcessingException e) {
            System.err.println("It's a mistake with Json: " + e.getMessage());
            return null;
        }
    }

    public static void exists(String valid) {
        if (valid == null) {
            throw new NullPointerException();
        }
    }

    public static String codeMessage(Order order, String phone) {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(order.getId());
        hash = 79 * hash + Objects.hashCode(order.getIdRestaurant().getPhone());
        hash = 79 * hash + Objects.hashCode(phone);
        return String.valueOf(Math.abs(hash));
    }

}
