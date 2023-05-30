package com.pragma.powerup.usermicroservice.domain.exceptions;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super("Invalid user: User is not a valid owner.");
    }
}
