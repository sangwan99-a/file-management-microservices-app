package com.example.file.controller;

import com.example.file.model.FileMetadata;
import com.example.file.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// Basic DTO for upload response
class UploadFileResponse {
    private Long id;
    private String fileName;
    private String fileDownloadUri; // Example, not fully implemented here
    private String fileType;
    private long size;

    public UploadFileResponse(Long id, String fileName, String fileDownloadUri, String fileType, long size) {
        this.id = id;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileDownloadUri() { return fileDownloadUri; }
    public void setFileDownloadUri(String fileDownloadUri) { this.fileDownloadUri = fileDownloadUri; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
}


@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file,
                                                   @RequestParam(value = "userId", required = false) Long userId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Or custom error response
        }
        try {
            FileMetadata metadata = fileStorageService.storeFile(file, userId);
            // Construct a basic response - in a real app, you might generate a download URI
            String fileDownloadUri = "/api/files/download/" + metadata.getId(); // Example URI

            UploadFileResponse response = new UploadFileResponse(
                    metadata.getId(),
                    metadata.getFileName(),
                    fileDownloadUri, // This is just an example, download endpoint not implemented yet
                    metadata.getFileType(),
                    metadata.getSize()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Log exception e
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Or custom error response
        }
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileMetadata> getFileMetadata(@PathVariable Long fileId) {
        try {
            FileMetadata metadata = fileStorageService.getFileMetadata(fileId);
            return ResponseEntity.ok(metadata);
        } catch (RuntimeException e) { // Catching RuntimeException from service for "not found"
            return ResponseEntity.notFound().build();
        }
    }

    // TODO: Implement download endpoint if needed
    // @GetMapping("/download/{fileId}")
    // public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId, HttpServletRequest request) { ... }
}