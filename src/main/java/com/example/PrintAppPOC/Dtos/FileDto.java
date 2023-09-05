package com.example.PrintAppPOC.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String fileName;
    private String fileType;
    private String color;
    private String startPage;
    private String endPage;
    private Integer numberOfCopies;
}
