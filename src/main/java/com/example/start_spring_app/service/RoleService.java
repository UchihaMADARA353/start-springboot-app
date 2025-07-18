package com.example.start_spring_app.service;

import com.example.start_spring_app.dto.RoleDTO;
import com.example.start_spring_app.enumType.RoleName;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO getRoleById(UUID id);
    RoleDTO getRoleByName(RoleName roleName);
    RoleDTO updateRole(UUID id, RoleDTO roleDTO);
    void deleteRole(UUID id);
}
