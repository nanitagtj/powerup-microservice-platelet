package com.pragma.powerup.usermicroservice.domain.orderMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragma.powerup.usermicroservice.domain.model.OrderLogJson;

public class OrderLogJsonSerialize {

    public static String serializeToJson(OrderLogJson orderLogJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(orderLogJson);
        } catch (JsonProcessingException e) {
            System.err.println("Mistake to serialize to JSON: " + e.getMessage());
            return null;
        }
    }
}
