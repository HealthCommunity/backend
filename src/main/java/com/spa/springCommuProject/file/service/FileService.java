package com.spa.springCommuProject.file.service;//package com.spa.springCommuProject.posts.service;

import com.spa.springCommuProject.file.domain.FileDetail;
import com.spa.springCommuProject.file.repository.FileRepository;
import com.spa.springCommuProject.posts.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    @Transactional
    public String save(MultipartFile multipartFile, Post post) {
        return saveFile(multipartFile, post);
    }

    @Transactional
    public List<String> saveFiles(List<MultipartFile> files, Post post){
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(saveFile(file, post));
        }
        return urls;
    }

    private String saveFile(MultipartFile file, Post post) {
        FileDetail fileDetail = FileDetail.multipartOf(file, post);
        fileRepository.save(fileDetail);
        amazonS3ResourceStorage.store(fileDetail.getPath(), file);
        return "healthcommunitybucket.s3.ap-northeast-2.amazonaws.com/" + fileDetail.getPath();
    }


}
