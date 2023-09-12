package com.example.PrintAppPOC.Exception;

public class UserAlreadyExistsException extends RuntimeException{
    private String fieldName;
    private String fieldValue;

    public UserAlreadyExistsException(String fieldName, String fieldValue) {
        super(String.format("User Already exists with  %s : %s",fieldName,fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
