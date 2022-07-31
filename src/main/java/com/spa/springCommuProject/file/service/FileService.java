package com.spa.springCommuProject.file.service;//package com.spa.springCommuProject.posts.service;

import com.spa.springCommuProject.file.domain.FileDetail;
import com.spa.springCommuProject.file.domain.VideoCategory;
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
    public String saveFile(MultipartFile multipartFile, Post post, VideoCategory videoCategory) {
        return save(multipartFile, post, videoCategory);
    }

    @Transactional
    public List<String> saveFiles(List<MultipartFile> files, Post post){
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(save(file, post));
        }
        return urls;
    }

    private String save(MultipartFile file, Post post, VideoCategory videoCategory) {
        FileDetail fileDetail = FileDetail.multipartOf(file, post, videoCategory);
        fileRepository.save(fileDetail);
        amazonS3ResourceStorage.store(fileDetail.getPath(), file);
        return fileDetail.getUrl();
    }

    private String save(MultipartFile file, Post post) {
        FileDetail fileDetail = FileDetail.multipartOf(file, post);
        fileRepository.save(fileDetail);
        amazonS3ResourceStorage.store(fileDetail.getPath(), file);
        return fileDetail.getUrl();
    }

    @Transactional
    public List<String> updateFiles(List<MultipartFile> multipartFiles, Post post){
        List<String> urls = new ArrayList<>();
        List<FileDetail> files = post.getFiles();
        for (MultipartFile multipartFile : multipartFiles) {

        }

        return urls;
    }

    @Transactional
    public String updateFile(MultipartFile multipartFile, Post post){
        return update(multipartFile, post);
    }

    public String update(MultipartFile multipartFile, Post post){
        return "update";
    }


}
