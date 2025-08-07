package com.example.start_spring_app.controller;

import com.example.start_spring_app.dto.RoleDTO;
import com.example.start_spring_app.service.RoleService;
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
@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Gestion des Rôles")
public class RoleController {
    private final RoleService roleService;

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Récupérer tous les roles")
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Récupérer un role en fonction de son ID")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable UUID id) {
        RoleDTO role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Créer un role")
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return ResponseEntity.status(201).body(createdRole);
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Modifier un role")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable UUID id,
                                              @Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO updatedRole = roleService.updateRole(id, roleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @ApiResponse(responseCode = "204")
    @Operation(summary = "Supprimer un role en fonction de son ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
