package com.pragma.powerup.usermicroservice.domain.model;

import java.time.LocalDateTime;

public class OrderLogJson {

    private String id;
    private Long orderId;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime timestamp;

    public OrderLogJson() {
    }

    public OrderLogJson(String id, Long orderId, String previousStatus, String newStatus, LocalDateTime timestamp) {
        this.id = id;
        this.orderId = orderId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
