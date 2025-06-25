package com.example.file.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    private String fileType; // e.g., image/png, application/pdf

    private Long size; // in bytes

    @Column(nullable = false)
    private String storagePath; // Could be a path in a file system, an S3 URL, etc.

    private Long userId; // To link to the user who uploaded the file

    @Column(updatable = false) // Typically set on creation and not updated
    private LocalDateTime uploadTimestamp;

    // Constructors
    public FileMetadata() {
        this.uploadTimestamp = LocalDateTime.now();
    }

    public FileMetadata(String fileName, String fileType, Long size, String storagePath, Long userId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.storagePath = storagePath;
        this.userId = userId;
        this.uploadTimestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(LocalDateTime uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    // toString, equals, hashCode (optional but good practice)
}
