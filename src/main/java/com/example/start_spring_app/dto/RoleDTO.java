package com.example.start_spring_app.dto;

import com.example.start_spring_app.enumType.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RoleDTO(
        UUID id,
        @NotNull(message = "Ce champs ne peut pas être nul")
        RoleName roleName
) {}
