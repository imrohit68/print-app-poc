package com.example.PrintAppPOC.Dtos;

import lombok.Data;

@Data
public class JwtAuthResponseDto {
    private String userId;
    private String token;
}
