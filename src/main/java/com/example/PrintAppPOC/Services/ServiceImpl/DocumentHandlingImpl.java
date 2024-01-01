package com.example.PrintAppPOC.Services.ServiceImpl;

import com.aspose.words.Document;
import com.example.PrintAppPOC.Services.DocumentHandlingService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class DocumentHandlingImpl implements DocumentHandlingService {
    @Override
    public int getPageCount(MultipartFile file) {
        try {
            Document document = new Document(file.getInputStream());
            return document.getPageCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
