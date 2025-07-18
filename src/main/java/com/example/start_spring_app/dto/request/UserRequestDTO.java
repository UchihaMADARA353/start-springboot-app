package com.example.start_spring_app.dto.request;

import com.example.start_spring_app.dto.RoleDTO;
import lombok.Builder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
public record UserRequestDTO(
    UUID id,
    String email,
    String password,
    String passwordConfirmation,
    String photo,
    String name,
    Date birthDate
) {
}
