package com.example.PrintAppPOC.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTokenResponse {
    private String status;
    private JwtAuthResponseDto data;
}
