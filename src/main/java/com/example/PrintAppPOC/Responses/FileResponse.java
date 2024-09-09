package com.example.PrintAppPOC.Responses;

import com.example.PrintAppPOC.DataTransferObjects.FileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private List<FileDto> files;
    private List<String> unableToFetch;
}
