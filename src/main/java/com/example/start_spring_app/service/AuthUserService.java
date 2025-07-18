package com.example.start_spring_app.service;

import com.example.start_spring_app.dto.UserProfile;
import com.example.start_spring_app.dto.request.SignInrequestDTO;
import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.SignInResponseDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AuthUserService {
    SignInResponseDTO login(SignInrequestDTO request);
    UserProfile profile(Authentication authentication);
    UserResponseDTO updateProfile(UserRequestDTO requestDTO);
    void deleteProfile();
}
