package com.example.PrintAppPOC.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private String userName;
    private String token;
    private String mobileNumber;
}
