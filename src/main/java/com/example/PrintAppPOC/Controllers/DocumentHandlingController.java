package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.Responses.PageCountResponse;
import com.example.PrintAppPOC.Services.DocumentHandlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/documentHandling")
@RequiredArgsConstructor
public class DocumentHandlingController {

    private final DocumentHandlingService documentHandlingService;
    @PostMapping("/getPageCount")
    public ResponseEntity<PageCountResponse> getPageCount(@RequestParam("file") MultipartFile file){
        return new
                ResponseEntity<>
                (new PageCountResponse(documentHandlingService.getPageCount(file),true)
                        ,HttpStatus.OK);
    }
}
