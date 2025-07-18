package com.example.start_spring_app.service.impl;

import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.entities.Role;
import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.enumType.RoleName;
import com.example.start_spring_app.exception.UserAlreadyExistException;
import com.example.start_spring_app.exception.UserNotFoundException;
import com.example.start_spring_app.mapper.UserMapper;
import com.example.start_spring_app.repository.RoleRepository;
import com.example.start_spring_app.repository.UserRepository;
import com.example.start_spring_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userDTO, RoleName roleName) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
        roles.add(role);
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new UserAlreadyExistException("Email already exists");
        }
        if (!Objects.equals(userDTO.passwordConfirmation(), userDTO.password())) {
            throw new IllegalArgumentException("Password confirmation does not match");
        }
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return UserMapper.toDto(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return UserMapper.toDto(user);
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userDTO, RoleName roleName) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
        roles.add(role);

        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setEmail(userDTO.email());
        existingUser.setPassword(userDTO.password());
        existingUser.setPhoto(userDTO.photo());
        existingUser.setBirthDate(userDTO.birthDate());
        existingUser.setRoles(roles);
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
