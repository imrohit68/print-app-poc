package com.example.PrintAppPOC.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusResponse {
    private String message;
    private boolean success;
}
