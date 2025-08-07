package com.example.start_spring_app.service;

import com.example.start_spring_app.dto.response.UserResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public interface UserImageService {
    void addImage(MultipartFile file, Authentication authentication);
    Resource getProfilePhoto(Authentication authentication);
    Resource getImageById(Authentication authentication,UUID id);
}
