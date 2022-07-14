package com.spa.springCommuProject.file.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spa.springCommuProject.posts.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
public class FileDetail {

    protected FileDetail() {
    }

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    private String uploadFileName;
    private String storeFileName;

    @Enumerated(EnumType.STRING)
    private FileCategory fileCategory;

    private String format;
    private String path;
    private long bytes;


    public static FileDetail multipartOf(MultipartFile multipartFile, Post post) {
        final String uploadName = MultipartUtil.createFileUploadFileName();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());
        String formatCategory;
        if(format.equalsIgnoreCase("JPEG")||format.equalsIgnoreCase("JPG")||format.equalsIgnoreCase("PNG")
                ||format.equalsIgnoreCase("GIF") ||format.equalsIgnoreCase("PSD")
                ||format.equalsIgnoreCase("PDF")||format.equalsIgnoreCase("RAW")||format.equalsIgnoreCase("TIF")){
            formatCategory = FileCategory.IMAGE.name();
        }else{
            formatCategory = FileCategory.VIDEO.name();
        }

        return FileDetail.builder()
                .uploadFileName(uploadName)
                .storeFileName(multipartFile.getOriginalFilename())
                .format(format)
                .path(MultipartUtil.createPath(uploadName, format, formatCategory))
                .fileCategory(FileCategory.valueOf(formatCategory))
                .bytes(multipartFile.getSize())
                .post(post)
                .build();
    }
}
