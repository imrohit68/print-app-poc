package com.example.PrintAppPOC.DataTransferObjects;

import com.example.PrintAppPOC.Entities.Files;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private List<String> fileNames;
    private BigInteger orderAmount;
    private String paymentId;
    private String userId;
    private String storeId;
}
