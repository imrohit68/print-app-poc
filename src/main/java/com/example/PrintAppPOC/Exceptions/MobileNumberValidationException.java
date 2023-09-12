package com.example.PrintAppPOC.Exceptions;

public class MobileNumberValidationException extends RuntimeException{
    private String message;

    public MobileNumberValidationException(String message) {
        super(String.format(message));
        this.message = message;
    }
}
