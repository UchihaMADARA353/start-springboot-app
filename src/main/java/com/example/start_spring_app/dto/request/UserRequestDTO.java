package com.example.start_spring_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.Date;

@Builder
public record UserRequestDTO(
        @NotNull(message = "Ce champs ne peut pas être nul")
        @Email(message = "Entrez une adresse mail valide")
        String email,

        @Pattern(regexp = "")
        @NotNull(message = "Ce champs ne peut pas être nul")
        String password,

        @NotNull(message = "Ce champs ne peut pas être nul")
        String passwordConfirmation,

        @Null
        String photo,

        @NotNull(message = "Ce champs ne peut pas être nul")
        String name,

        @NotNull(message = "Ce chamsps ne peut pas être nul")
        Date birthDate
) { }
