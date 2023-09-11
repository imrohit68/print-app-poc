package com.example.PrintAppPOC.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTokenResponse {
    private String status;
    private JwtAuthResponseDto data;
}
