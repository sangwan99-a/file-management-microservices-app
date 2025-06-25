package com.example.file.repository;

import com.example.file.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

    // Example custom queries (can be added as needed):
    Optional<FileMetadata> findByFileName(String fileName);

    List<FileMetadata> findByUserId(Long userId);

    List<FileMetadata> findByFileType(String fileType);

}
