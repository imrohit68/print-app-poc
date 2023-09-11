package com.example.PrintAppPOC.Dtos;


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
    private String uniqueName;
    private String name;
    private Integer storeId;
}
