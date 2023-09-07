package com.example.PrintAppPOC.Dtos;

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
    private float longitude;
    private float latitude;
}