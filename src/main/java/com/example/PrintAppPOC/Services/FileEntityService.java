package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.Dtos.FileDto;

import java.util.List;


public interface FileEntityService {
    FileDto createFile(FileDto fileDto);
    FileDto updateFile(FileDto fileDto,String id);
    FileDto getById(String fileId);
    List<FileDto> getAllFiles();
    void deleteFile(String fileId);
}
