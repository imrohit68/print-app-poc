package com.example.PrintAppPOC.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreLoginResponse {
    private boolean success;
    private String message;
    private DataStoreLogin data;
}
