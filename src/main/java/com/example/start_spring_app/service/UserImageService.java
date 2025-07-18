package com.example.start_spring_app.service;

import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserImageService {
    void addImage(String email, MultipartFile file);
    void updateImage(MultipartFile file, UUID userId);

    Resource getProfilePhoto(Authentication authentication);
}
