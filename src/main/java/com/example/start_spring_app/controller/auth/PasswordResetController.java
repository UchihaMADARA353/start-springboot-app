package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-password")
@Tag(name = "Mise à jour du mot de passe")
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    @Operation(summary = "Mot de passe oublié")
    @ApiResponse(responseCode = "200")
    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        passwordResetService.forgetPassword(email);
        return ResponseEntity.ok("une email vous a été envoyé");
    }

    @Operation(summary = "Modifier le mot de passe")
    @ApiResponse(responseCode = "201")
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String token,
                                           @RequestParam String password,
                                           @RequestParam String passwordConfirmation) {
        passwordResetService.resetPassword(email, token, password, passwordConfirmation);
        return ResponseEntity.ok("Mot de passe modifier avec succes");
    }
}
