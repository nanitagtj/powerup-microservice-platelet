package com.pragma.powerup.usermicroservice.domain.exceptions;

public class OrderNoFoundException extends RuntimeException {
    private final Long idOrder;

    public OrderNoFoundException(Long idOrder) {
        super();
        this.idOrder = idOrder;
    }

    public Long getIdOrder() {
        return idOrder;
    }
}
