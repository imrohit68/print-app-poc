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
    private int id;
    private String mobileNumber;
    private String storeName;
    private String location;
}
