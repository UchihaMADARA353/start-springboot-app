package com.example.start_spring_app.service.impl;

import com.example.start_spring_app.entities.Role;
import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.enumType.RoleName;
import com.example.start_spring_app.exception.RoleNotFoundException;
import com.example.start_spring_app.repository.RoleRepository;
import com.example.start_spring_app.repository.UserRepository;
import com.example.start_spring_app.service.AssignRoleToUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AssignRoleToUserServiceImpl implements AssignRoleToUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void assignRoleToUser(String email, RoleName roleName) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RoleNotFoundException("User not found with email: " + email));
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
