package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.service.ChangeEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/change-email")
@Tag(name = "Modifier son adresse email")
public class ChangeEmailController {
    private final ChangeEmailService changeEmail;

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Changer l'email")
    @PostMapping
    public ResponseEntity<?> changeEmail(@RequestParam String email,
                                         @RequestParam String newEmail,
                                         Authentication authentication) {
        changeEmail.changeEmail(authentication, email, newEmail);
        return ResponseEntity.ok("Email modifier avec succès");
    }
}
