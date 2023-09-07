package com.example.PrintAppPOC.Dtos;

import com.example.PrintAppPOC.Entity.Files;
import com.example.PrintAppPOC.Entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private List<Files> fileNames;
}
