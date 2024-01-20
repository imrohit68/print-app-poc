package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.DataTransferObjects.FileDto;
import org.apache.poi.util.LocaleID;

import java.util.List;


public interface FileService {
    FileDto createFile(FileDto fileDto);
    FileDto updateFile(FileDto fileDto,String id);
    List<FileDto> getById(List<String> fileId);
    List<FileDto> getAllFiles();
    void deleteFile(String fileId);
}
