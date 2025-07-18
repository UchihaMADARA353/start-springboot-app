package com.example.start_spring_app.dto.response;

import com.example.start_spring_app.dto.RoleDTO;
import lombok.Builder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
public record UserResponseDTO(
    UUID id,
    String email,
    String password,
    String photo,
    String name,
    Date birthDate,
    List<RoleDTO> roles
) { }
