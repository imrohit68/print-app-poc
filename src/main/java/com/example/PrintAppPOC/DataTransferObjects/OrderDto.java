package com.example.PrintAppPOC.DataTransferObjects;

import com.example.PrintAppPOC.Entities.Files;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private List<Files> fileNames;
}
