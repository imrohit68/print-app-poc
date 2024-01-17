package com.example.PrintAppPOC.DataTransferObjects;


import com.example.PrintAppPOC.Entities.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String mobileNumber;
    private String name;
    private String icon;
    private Store store;
}
