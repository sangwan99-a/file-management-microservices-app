package com.example.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    private static final String BASE_DIR = "local-storage";

    public String storeFile(MultipartFile file) {
        String type = getFileType(file.getOriginalFilename());
        String dirPath = BASE_DIR + "/" + type;
        new File(dirPath).mkdirs();

        File dest = new File(dirPath + "/" + file.getOriginalFilename());
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return "Failed to store file: " + e.getMessage();
        }

        return "File stored at: " + dest.getAbsolutePath();
    }

    private String getFileType(String filename) {
        String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (ext) {
            case "jpg":
            case "jpeg":
            case "png":
                return "images";
            case "mp4":
            case "avi":
                return "videos";
            case "pdf":
            case "docx":
                return "documents";
            case "mp3":
            case "wav":
                return "audio";
            default:
                return "others";
        }
    }
}