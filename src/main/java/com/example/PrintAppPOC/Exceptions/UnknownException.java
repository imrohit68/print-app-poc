package com.example.PrintAppPOC.Exceptions;

public class UnknownException extends RuntimeException {
    private String exceptionMessage;
    public UnknownException(String exceptionMessage){
        super(String.format(exceptionMessage));
        this.exceptionMessage = exceptionMessage;
    }
}
