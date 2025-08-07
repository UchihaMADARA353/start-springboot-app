package com.example.start_spring_app.controller.auth;

import com.example.start_spring_app.dto.PersonalAccessTokenDTO;
import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.service.RegisterUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/register")
@Tag(name = "Inscription d'un nouvel utilisateur")
public class RegisterUserController {
    private final RegisterUserService registerUserService;

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Enregistrer un nouvel utilisateur")
    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userDTO) {
        UserResponseDTO registeredUser = registerUserService.registerUser(userDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Récupérer l'email de l'utilisateur")
    @GetMapping("{email}")
    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email) {
        UserResponseDTO user = registerUserService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Générer un token pour l'inscription")
    @GetMapping("/token")
    public ResponseEntity<?> generateToken(@RequestParam String email) {
        registerUserService.generateToken(email);
        return ResponseEntity.ok("Token générer avec succès");
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Confirmer son compte utilisateur")
    @PostMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestBody PersonalAccessTokenDTO tokenDTO) {
        registerUserService.confirmAccount(tokenDTO);
        return ResponseEntity.ok("Compte activer avec succès");
    }
}
