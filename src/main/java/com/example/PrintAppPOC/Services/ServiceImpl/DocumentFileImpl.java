package com.example.PrintAppPOC.Services.ServiceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.example.PrintAppPOC.Services.FileDocumentService;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentFileImpl implements FileDocumentService {

    private final AmazonS3 amazonS3;

    private String bucketName = "shopdocument";

    private final Storage storage;

    @Override
    public ByteArrayResource downloadFile(String fileName) throws IOException {
        S3Object object = amazonS3.getObject(bucketName, fileName);
        byte[] bytes = object.getObjectContent().readAllBytes();
        return new ByteArrayResource(bytes);
    }

    @Override
    public boolean deleteFile(String fileName) {
        amazonS3.deleteObject(bucketName,fileName);
        return true;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileId = UUID.randomUUID()+"."+file.getOriginalFilename().split("\\.")[1];
        amazonS3.putObject(bucketName,fileId,file.getInputStream(),null);
        return fileId;
    }
}
