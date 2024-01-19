package com.example.PrintAppPOC.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FetchOrderResponse {
    private List<String> fileNames;
    private BigInteger orderAmount;
    private String userId;
    private LocalDateTime localDateTime;
}
