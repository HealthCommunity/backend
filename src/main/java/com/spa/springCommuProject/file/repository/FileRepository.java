package com.spa.springCommuProject.file.repository;

import com.spa.springCommuProject.file.domain.FileDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDetail, Long> {
}
