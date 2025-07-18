package com.example.start_spring_app.service.strategy;

import org.springframework.web.multipart.MultipartFile;

public interface UploadStrategy {
    String uploadFile(MultipartFile file);
}
