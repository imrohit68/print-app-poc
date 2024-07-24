package com.example.PrintAppPOC.Requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OtpSendRequest {
    @NotEmpty(message = "Mobile number can not be a null or empty")
    @Pattern(regexp="(^$|\\+91[0-9]{10})",message = "Mobile Number must be in the format +91XXXXXXXXXX")
    private String mobileNumber;
}
