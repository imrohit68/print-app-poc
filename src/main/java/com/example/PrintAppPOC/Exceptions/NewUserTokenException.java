package com.example.PrintAppPOC.Exceptions;

public class NewUserTokenException extends RuntimeException{
    private String mobileNumber;

    public NewUserTokenException(String mobileNumber) {
        super(String.format(mobileNumber));
        this.mobileNumber = mobileNumber;
    }
}
