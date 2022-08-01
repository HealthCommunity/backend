package com.spa.springCommuProject.file.repository;

import com.spa.springCommuProject.file.domain.FileDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileDetail, Long> {

    List<FileDetail> findByPostId(Long postId);
}
