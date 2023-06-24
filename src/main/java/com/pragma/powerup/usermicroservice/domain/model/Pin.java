package com.pragma.powerup.usermicroservice.domain.model;

public class Pin {
    private Long id;
    private Order orderId;
    private String pin;

    public Pin() {
    }

    public Pin(Long id, Order orderId, String pin) {
        this.id = id;
        this.orderId = orderId;
        this.pin = pin;
    }

    public Pin(Order order, String pin) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
