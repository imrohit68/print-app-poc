package com.example.PrintAppPOC.Exception;



public class CantCreateToken extends RuntimeException{
    private String message;
    public CantCreateToken(String message){
        super(String.format(message));
        this.message = message;
    }
}
