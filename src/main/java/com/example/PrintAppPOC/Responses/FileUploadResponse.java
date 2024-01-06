package com.example.PrintAppPOC.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileUploadResponse {
    private String fileId;
    private boolean status;
}
