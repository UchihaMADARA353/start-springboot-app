package com.example.start_spring_app.mapper;

import com.example.start_spring_app.dto.RoleDTO;
import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.entities.Role;
import com.example.start_spring_app.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public static UserResponseDTO toDto(User user) {
        List<RoleDTO> roles = user.getRoles().stream()
                .map(RoleMapper::toDto)
                .toList();
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .photo(user.getPhoto())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .roles(roles)
                .build();
    }

    public static User toEntity(UserRequestDTO userDTO) {
        return User.builder()
                .email(userDTO.email())
                .password(userDTO.password())
                .name(userDTO.name())
                .photo(userDTO.photo())
                .birthDate(userDTO.birthDate())
                .build();
    }
}
