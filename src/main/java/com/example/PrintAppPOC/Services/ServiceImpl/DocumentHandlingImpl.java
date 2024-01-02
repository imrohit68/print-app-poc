package com.example.PrintAppPOC.Services.ServiceImpl;

import com.example.PrintAppPOC.Services.DocumentHandlingService;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.SlideShowFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class DocumentHandlingImpl implements DocumentHandlingService {
    @Override
    public int getPageCount(MultipartFile file) {
        try {
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
            if(fileType.equals("docx")){
                XWPFDocument document = new XWPFDocument(file.getInputStream());
                return document.getProperties().getExtendedProperties().getPages();
            } else if (fileType.equals("pptx")) {
                SlideShow slideShow = SlideShowFactory.create(file.getInputStream());
                return slideShow.getSlides().size();
            }
            else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
