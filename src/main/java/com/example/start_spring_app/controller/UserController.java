package com.example.start_spring_app.controller;

import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.enumType.RoleName;
import com.example.start_spring_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Tag(name= "Gestion des Utilisateurs", description = "API pour la gestion des utilisateurs")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Récupérer tous les utilisateurs")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Récupérer un utilisateur en fonction de son ID")
    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Créer un nouvel utilisateur")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO user,
                                                      @RequestParam RoleName roleName) {
        UserResponseDTO createUser = userService.createUser(user, roleName);
        return ResponseEntity.status(201).body(createUser);
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Modifier un utilisateur")
    @PutMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO user,
                                                      @PathVariable UUID id,
                                                      @RequestParam RoleName roleName) {
        UserResponseDTO updateUser = userService.updateUser(id, user, roleName);
        return ResponseEntity.ok(updateUser);
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "Supprimer un utilisateur en fonction de son ID")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
