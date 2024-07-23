package com.example.PrintAppPOC.Services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileDocumentService {
    ByteArrayResource downloadFile(String fileName) throws IOException;
    boolean deleteFile(String fileName);
    String uploadFile(MultipartFile file) throws IOException;
}
