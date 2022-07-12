package com.spa.springCommuProject.file.service;//package com.spa.springCommuProject.posts.service;

import com.spa.springCommuProject.file.domain.FileDetail;
import com.spa.springCommuProject.file.repository.FileRepository;
import com.spa.springCommuProject.posts.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    @Transactional
    public String save(MultipartFile multipartFile, Post post) {
        FileDetail fileDetail = FileDetail.multipartOf(multipartFile, post);
        //FileDetail fileDetail = FileDetail.multipartOf(multipartFile, User user);
        fileRepository.save(fileDetail);
        amazonS3ResourceStorage.store(fileDetail.getPath(), multipartFile);
        return fileDetail.getPath();
    }


}
