package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.service.UserImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/photo-user")
@Tag(name = "Gestion de l'Upload de l'image")
public class UserImageController {
    private final UserImageService addUserImageService;

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Uploader une nouvelle image")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImage(
            Authentication authentication,
            @RequestParam MultipartFile file) {
        addUserImageService.addImage(file, authentication);
        return ResponseEntity.ok("Image uploader avec succès");
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Récupérer la photo de profile")
    @GetMapping
    public ResponseEntity<Resource> getProfilePhoto(Authentication authentication) throws IOException {
        Resource resource = addUserImageService.getProfilePhoto(authentication);
        return getResourceResponseEntity(resource);
    }


    @ApiResponse(responseCode = "200")
    @Operation(summary = "Récupérer la photo de profile")
    @GetMapping("{id}")
    public ResponseEntity<Resource> getProfilePhoto(Authentication authentication, @PathVariable UUID id) throws IOException {
        Resource resource = addUserImageService.getImageById(authentication, id);
        return getResourceResponseEntity(resource);
    }

    @NotNull
    private ResponseEntity<Resource> getResourceResponseEntity(Resource resource) throws IOException {
        String contentType = Files.probeContentType(resource.getFile().toPath());

        String fileName = resource.getFilename(); // récupère le nom du fichier
        if (fileName == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile photo not found");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
