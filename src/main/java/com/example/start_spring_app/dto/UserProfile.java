package com.example.start_spring_app.dto;

import com.example.start_spring_app.enumType.RoleName;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record UserProfile(
        boolean isAuthenticatd,
        List<RoleName> roles,
        String name,
        String email,
        Date birthDate,
        boolean isActived

) {
}
