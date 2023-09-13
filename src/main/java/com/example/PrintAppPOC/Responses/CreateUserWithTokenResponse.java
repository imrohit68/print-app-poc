package com.example.PrintAppPOC.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserWithTokenResponse {
    private boolean success;
    private String token;
    private String message;
}
