package com.example.PrintAppPOC.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Files {
    @Id
    private String fileName;
    private String fileType;
    private String color;
    private String startPage;
    private String endPage;
    private Integer numberOfCopies;
}
