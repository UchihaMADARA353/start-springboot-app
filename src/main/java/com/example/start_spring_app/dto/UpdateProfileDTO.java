package com.example.start_spring_app.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record UpdateProfileDTO(
        @NotNull(message = "Ce champs ne peut pas être nul")
        String name,

        @NotNull(message = "Ce champs ne peut pas être nul")
        Date birthDate
) {
}
