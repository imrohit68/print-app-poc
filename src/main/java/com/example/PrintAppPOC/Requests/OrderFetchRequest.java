package com.example.PrintAppPOC.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderFetchRequest {
    private String storeId;
    private int pageNumber;
}
