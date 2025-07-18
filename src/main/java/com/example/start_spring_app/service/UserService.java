package com.example.start_spring_app.service;

import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.enumType.RoleName;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO createUser(UserRequestDTO userDTO, RoleName roleName);
    UserResponseDTO getUserById(UUID id);
    UserResponseDTO getUserByEmail(String email);
    UserResponseDTO updateUser(UUID id, UserRequestDTO userDTO, RoleName roleName);
    void deleteUser(UUID id);
}
