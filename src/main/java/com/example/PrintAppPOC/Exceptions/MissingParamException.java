package com.example.PrintAppPOC.Exceptions;

public class MissingParamException extends RuntimeException{
    private String message;

    public MissingParamException(String message) {
        super(String.format(message));
        this.message = message;
    }
}
