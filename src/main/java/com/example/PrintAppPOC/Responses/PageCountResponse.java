package com.example.PrintAppPOC.Responses;


import lombok.*;

@Data
@AllArgsConstructor
public class PageCountResponse {
    private int count;
    private boolean status;
}
