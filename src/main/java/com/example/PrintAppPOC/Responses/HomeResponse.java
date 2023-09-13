package com.example.PrintAppPOC.Responses;

import com.example.PrintAppPOC.DataTransferObjects.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HomeResponse {
    private String mobileNumber;
    private String userName;
    private String token;
}
