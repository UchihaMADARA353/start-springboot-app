package com.example.start_spring_app.dto;

import com.example.start_spring_app.enumType.RoleName;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RoleDTO(
    UUID id,
    RoleName roleName
) {}
