package com.example.start_spring_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignInrequestDTO(
        @NotNull(message = "Ce champ ne peut pas être null")
        @Size(min = 3, max = 100, message = "Le titre doit contenir entre 3 et 100 caractères")
        @Email(message = "Entrez une email valide")
        String email,

        String password
) {
}
