package com.example.start_spring_app.service.impl.auth;

import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.exception.FileNotFoundException;
import com.example.start_spring_app.exception.UserNotFoundException;
import com.example.start_spring_app.repository.UserRepository;
import com.example.start_spring_app.service.UserImageService;
import com.example.start_spring_app.service.strategy.LoadFileStrategy;
import com.example.start_spring_app.service.strategy.UploadStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserImageServiceImpl implements UserImageService {
    private final UserRepository userRepository;
    private final UploadStrategy uploadStrategy;
    private final LoadFileStrategy loadFileStrategy;

    @Override
    public void addImage(MultipartFile file, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);
        String image = uploadStrategy.uploadFile(file);
        user.ifPresent(getUser -> getUser.setPhoto(image));
        user.ifPresent(userRepository::save);
    }

    @Override
    public Resource getImageById(Authentication authentication, UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Email introuvable"));
        String filename = user.getPhoto();
        if (filename == null || filename.isEmpty()) {
            throw new FileNotFoundException("File not found");
        }
        try {
            return loadFileStrategy.loadFile(filename);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @Override
    public Resource getProfilePhoto(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String filename = user.getPhoto();
        if (filename == null ||  filename.isBlank()) {
            throw new FileNotFoundException("File not found");
        }

        try {
            return loadFileStrategy.loadFile(filename);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
