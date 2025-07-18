package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.dto.UserProfile;
import com.example.start_spring_app.dto.request.SignInrequestDTO;
import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.SignInResponseDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.service.AuthUserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthUserController {
    private final AuthUserService authUserService;

    @ApiResponse(responseCode = "200")
    @PostMapping("/login")
    public ResponseEntity<SignInResponseDTO> login(@Valid @RequestBody SignInrequestDTO sign) {
        SignInResponseDTO login = authUserService.login(sign);
        return ResponseEntity.ok(login);
    }

    @ApiResponse(responseCode = "200", content = @Content(mediaType = "image/*"))
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> profile(Authentication authentication) {
        UserProfile profile = authUserService.profile(authentication);
        return ResponseEntity.ok(profile);
    }

    @ApiResponse(responseCode = "200")
    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateProfile(UserRequestDTO requestDTO) {
        UserResponseDTO user = authUserService.updateProfile(requestDTO);
        return ResponseEntity.ok(user);
    }

    @ApiResponse(responseCode = "204")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProfile() {
        authUserService.deleteProfile();
        return ResponseEntity.noContent().build();
    }
}
