package com.example.PrintAppPOC.Responses;

import com.example.PrintAppPOC.DataTransferObjects.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTokenResponse {
    private boolean newUser;
    private Info data;
}
