package com.example.PrintAppPOC.Dtos;

import lombok.Data;

@Data
public class JwtAuthRequestDto {
    private String mobileNumber;
    private String otp;
}
