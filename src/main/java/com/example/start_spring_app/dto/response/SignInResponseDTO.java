package com.example.start_spring_app.dto.response;

import com.example.start_spring_app.dto.RoleDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record SignInResponseDTO(
        String username,
        List<String> roles,
        String token
) {
}
