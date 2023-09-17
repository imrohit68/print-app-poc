package com.example.PrintAppPOC.Exceptions;

public class InvalidTokenException extends RuntimeException{
    private String message;

    public InvalidTokenException(String message) {
        super(String.format(message));
        this.message = message;
    }
}
