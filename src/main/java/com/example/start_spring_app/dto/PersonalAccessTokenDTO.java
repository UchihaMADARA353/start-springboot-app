package com.example.start_spring_app.dto;

import lombok.Builder;

@Builder
public record PersonalAccessTokenDTO(
        Long id,
        String email,
        String token
) {
}
