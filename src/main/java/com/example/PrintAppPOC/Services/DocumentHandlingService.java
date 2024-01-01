package com.example.PrintAppPOC.Services;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentHandlingService {
    int getPageCount(MultipartFile file);
}
