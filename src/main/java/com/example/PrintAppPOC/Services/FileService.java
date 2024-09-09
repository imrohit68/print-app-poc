package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.DataTransferObjects.FileDto;
import com.example.PrintAppPOC.Responses.FileResponse;

import java.util.List;


public interface FileService {
    FileDto createFile(FileDto fileDto);
    FileDto updateFile(FileDto fileDto,String id);
    FileResponse getById(List<String> fileId);
    List<FileDto> getAllFiles();
    void deleteFile(String fileId);
}
