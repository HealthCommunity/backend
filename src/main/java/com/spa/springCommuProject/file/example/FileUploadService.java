package com.spa.springCommuProject.file.example;

import com.spa.springCommuProject.file.service.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public FileDetailOld save(MultipartFile multipartFile) {
        FileDetailOld fileDetailOld = FileDetailOld.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(fileDetailOld.getPath(), multipartFile);
        return fileDetailOld;
    }
}
