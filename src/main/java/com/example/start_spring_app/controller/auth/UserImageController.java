package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/photo-user")
public class UserImageController {
    private final UserImageService addUserImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImage(@RequestParam String email,
                                      @RequestParam MultipartFile file) {
        addUserImageService.addImage(email, file);
        return ResponseEntity.ok("Image uploader avec succès");
    }

    @GetMapping
    public ResponseEntity<Resource> getProfilePhoto(Authentication authentication) throws IOException {
        Resource resource = addUserImageService.getProfilePhoto(authentication);
        String contentType = Files.probeContentType(resource.getFile().toPath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream")
                .body(resource);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@RequestParam MultipartFile file,
                                         @PathVariable UUID id) {
        addUserImageService.updateImage(file, id);
        return ResponseEntity.ok("Image modifier avec succès");
    }
}
