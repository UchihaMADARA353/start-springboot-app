package com.example.start_spring_app.service.impl;

import com.example.start_spring_app.dto.RoleDTO;
import com.example.start_spring_app.entities.Role;
import com.example.start_spring_app.enumType.RoleName;
import com.example.start_spring_app.exception.RoleNotFoundException;
import com.example.start_spring_app.mapper.RoleMapper;
import com.example.start_spring_app.repository.RoleRepository;
import com.example.start_spring_app.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toDto)
                .toList();
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = RoleMapper.toEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toDto(savedRole);
    }

    @Override
    public RoleDTO getRoleById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + id));
        return RoleMapper.toDto(role);
    }

    @Override
    public RoleDTO getRoleByName(RoleName roleName) {
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found with name: " + roleName));
        return RoleMapper.toDto(role);
    }

    @Override
    public RoleDTO updateRole(UUID id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + id));
        existingRole.setRoleName(roleDTO.roleName());
        Role updatedRole = roleRepository.save(existingRole);
        return RoleMapper.toDto(updatedRole);
    }

    @Override
    public void deleteRole(UUID id) {
        roleRepository.deleteById(id);
    }
}
