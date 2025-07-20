package com.example.start_spring_app.controller;

import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.enumType.RoleName;
import com.example.start_spring_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user,
                                                      @RequestParam RoleName roleName) {
        UserResponseDTO createUser = userService.createUser(user, roleName);
        return ResponseEntity.status(201).body(createUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO user,
                                                      @PathVariable UUID id,
                                                      @RequestParam RoleName roleName) {
        UserResponseDTO updateUser = userService.updateUser(id, user, roleName);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
