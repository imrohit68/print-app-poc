package com.example.PrintAppPOC.Requests;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String mobileNumber;
    private String otp;
}
