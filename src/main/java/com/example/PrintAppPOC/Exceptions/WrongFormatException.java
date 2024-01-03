package com.example.PrintAppPOC.Exceptions;

public class WrongFormatException extends RuntimeException{
    private String message;

    public WrongFormatException(String message) {
        super(String.format(message));
        this.message = message;
    }
}
