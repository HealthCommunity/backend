package com.spa.springCommuProject.file.example;

import com.spa.springCommuProject.file.domain.MultipartUtil;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileDetailOld {
    private String id;
    private String name;
    private String format;
    private String path;
    private long bytes;

    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now();
    private File file;
    public static FileDetailOld multipartOf(MultipartFile multipartFile) {
        final String fileId = MultipartUtil.createFileUploadFileName();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());
        return FileDetailOld.builder()
                .id(fileId)
                .name(multipartFile.getOriginalFilename())
                .format(format)
                .path(MultipartUtil.createPath(fileId, format))
                .bytes(multipartFile.getSize())
                .build();
    }
}
