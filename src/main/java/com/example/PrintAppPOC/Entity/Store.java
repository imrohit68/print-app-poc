package com.example.PrintAppPOC.Entity;


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
public class Store {
    @Id
    private String mobileNumber;
    private String storeName;
    private float longitude;
    private float latitude;
}
