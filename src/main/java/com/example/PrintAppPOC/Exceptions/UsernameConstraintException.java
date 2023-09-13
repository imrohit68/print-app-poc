package com.example.PrintAppPOC.Exceptions;

public class UsernameConstraintException extends RuntimeException {
    private String message;

    public UsernameConstraintException(String message) {
        super(String.format(message));
        this.message = message;
    }
}
