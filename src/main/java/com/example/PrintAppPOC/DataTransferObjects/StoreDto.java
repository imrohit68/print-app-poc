package com.example.PrintAppPOC.DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private String mobileNumber;
    private String storeName;
    private String longitude;
    private String latitude;
    private String streetAddress;
}