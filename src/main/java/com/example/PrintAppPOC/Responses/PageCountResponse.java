package com.example.PrintAppPOC.Responses;


import lombok.*;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageCountResponse {
    private int count;
    private boolean status;
}
