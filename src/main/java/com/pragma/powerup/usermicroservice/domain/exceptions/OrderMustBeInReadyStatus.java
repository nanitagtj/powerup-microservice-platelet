package com.pragma.powerup.usermicroservice.domain.exceptions;

import org.aspectj.weaver.ast.Or;

public class OrderMustBeInReadyStatus extends RuntimeException{

    public OrderMustBeInReadyStatus() {super();}
}
