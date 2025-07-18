package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.dto.PersonalAccessTokenDTO;
import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.service.RegisterUserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth/register")
public class RegisterUserController {
    private final RegisterUserService registerUserService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userDTO) {
        UserResponseDTO registeredUser = registerUserService.registerUser(userDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/token")
    public ResponseEntity<?> generateToken(@RequestParam String email) {
        registerUserService.generateToken(email);
        return ResponseEntity.ok("Token générer avec succès");
    }

    @PostMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestBody PersonalAccessTokenDTO tokenDTO) {
        registerUserService.confirmAccount(tokenDTO);
        return ResponseEntity.ok("Compte activer avec succès");
    }
}
