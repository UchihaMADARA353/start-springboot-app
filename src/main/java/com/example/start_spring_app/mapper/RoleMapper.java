package com.example.start_spring_app.mapper;

import com.example.start_spring_app.dto.RoleDTO;
import com.example.start_spring_app.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public static RoleDTO toDto(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static Role toEntity(RoleDTO role) {
        return Role.builder()
                .roleName(role.roleName())
                .build();
    }
}
