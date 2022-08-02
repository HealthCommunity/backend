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
import java.util.stream.Collectors;

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
    public List<String> saveFiles(List<MultipartFile> files, Post post) {
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
    public List<String> updateFiles(List<MultipartFile> multipartFiles, Post post) {
        List<FileDetail> files = post.getFiles();
        List<String> saveFilesName = files.stream().map(x -> x.getStoreFileName()).collect(Collectors.toList());
        List<String> newFilesName = multipartFiles.stream().map(x -> x.getOriginalFilename()).collect(Collectors.toList());

        for (FileDetail file : files) {
            if (newFilesName.contains(file.getStoreFileName())) {
                continue;
            } else {
                fileRepository.deleteById(file.getId());
            }
        }

        for (MultipartFile multipartFile : multipartFiles) {
            if (saveFilesName.contains(multipartFile.getOriginalFilename())) {
                continue;
            } else {
                save(multipartFile, post);
            }
        }
        List<FileDetail> updatePostFiles = fileRepository.findByPostId(post.getId());
        List<String> urls = updatePostFiles.stream().map(x -> x.getUrl()).collect(Collectors.toList());
        return urls;
    }

    @Transactional
    public String updateFile(MultipartFile multipartFile, Post post, VideoCategory videoCategory) {
        return update(multipartFile, post, videoCategory);
    }

    public String update(MultipartFile multipartFile, Post post, VideoCategory videoCategory) {
        List<FileDetail> files = post.getFiles();
        FileDetail fileDetail = files.stream().filter(x -> x.getPath().contains(videoCategory.name())).findAny().get();
        if (!fileDetail.getStoreFileName().equals(multipartFile.getOriginalFilename())) { //원래 있던거랑 새로온거랑 다르면
            System.out.println("삭제");
            fileRepository.delete(fileDetail); //원래 있던거 삭제
            return save(multipartFile, post, videoCategory);
        } else { //수정안했으면 그대로
            System.out.println("그대로");
            return fileDetail.getUrl();
        }
    }

}
