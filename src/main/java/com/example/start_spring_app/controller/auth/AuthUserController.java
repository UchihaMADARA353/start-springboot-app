package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.dto.UpdateProfileDTO;
import com.example.start_spring_app.dto.UserProfile;
import com.example.start_spring_app.dto.request.SignInrequestDTO;
import com.example.start_spring_app.dto.response.SignInResponseDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.service.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentification de l'utilisateur")
public class AuthUserController {
    private final AuthUserService authUserService;

    @Operation(summary = "Authentification")
    @ApiResponse(responseCode = "200")
    @PostMapping("/login")
    public ResponseEntity<SignInResponseDTO> login(@Valid @RequestBody SignInrequestDTO sign) {
        SignInResponseDTO login = authUserService.login(sign);
        return ResponseEntity.ok(login);
    }

    @Operation(summary = "Profile Utilisateur")
    @ApiResponse(responseCode = "200")
    @GetMapping(value = "/profile")
    public ResponseEntity<UserProfile> profile(Authentication authentication) {
        UserProfile profile = authUserService.profile(authentication);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Déconnecter l'utilisateur")
    @ApiResponse(responseCode = "200")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authUserService.logout(request, response);
        return ResponseEntity.ok().body("Déconnecté avec succès !");
    }

    @Operation(summary = "Modifier le profile")
    @ApiResponse(responseCode = "200")
    @PutMapping("/update/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(@Valid @RequestBody UpdateProfileDTO requestDTO) {
        UserResponseDTO user = authUserService.updateProfile(requestDTO);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Supprimer le profile de l'utilisateur")
    @ApiResponse(responseCode = "204")
    @DeleteMapping("/delete/profile")
    public ResponseEntity<Void> deleteProfile() {
        authUserService.deleteProfile();
        return ResponseEntity.noContent().build();
    }
}
