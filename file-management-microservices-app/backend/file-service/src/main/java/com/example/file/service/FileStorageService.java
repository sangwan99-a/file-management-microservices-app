package com.example.file.service;

import com.example.file.model.FileMetadata;
import com.example.file.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final FileMetadataRepository fileMetadataRepository;

    @Autowired
    public FileStorageService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
        this.fileStorageLocation = Paths.get("local-storage")
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public FileMetadata storeFile(MultipartFile file, Long userId) {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            extension = originalFileName.substring(i);
        }
        // Generate a unique file name to prevent conflicts
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        try {
            // Check if the file's name contains invalid characters
            if (uniqueFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Create and save metadata
            FileMetadata metadata = new FileMetadata(
                    originalFileName, // Store original filename
                    file.getContentType(),
                    file.getSize(),
                    targetLocation.toString(), // Store the actual path of the stored file
                    userId // Pass userId, could be null if not provided or user is anonymous
            );
            return fileMetadataRepository.save(metadata);

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + originalFileName + ". Please try again!", ex);
        }
    }

    // Optional: Method to retrieve file metadata
    public FileMetadata getFileMetadata(Long fileId) {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
    }

    // Optional: Method to load file as Resource (to be implemented if download is needed)
    // public Resource loadFileAsResource(String fileName) { ... }
}