package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-password")
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        passwordResetService.forgetPassword(email);
        return ResponseEntity.ok("une email vous a été envoyé");
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String token,
                                           @RequestParam String password,
                                           @RequestParam String passwordConfirmation) {
        passwordResetService.resetPassword(email, token, password, passwordConfirmation);
        return ResponseEntity.ok("Mot de passe modifier avec succes");
    }
}
