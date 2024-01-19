package com.example.PrintAppPOC.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.value.qual.ArrayLen;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Data {
    private String storeId;
    private String token;
}
