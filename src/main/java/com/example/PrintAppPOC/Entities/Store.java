package com.example.PrintAppPOC.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store  {
    @Id
    private String mobileNumber;
    private String storeName;
    private String storeUsername;
    private String password;
    private String longitude;
    private String latitude;
    private String streetAddress;

}
