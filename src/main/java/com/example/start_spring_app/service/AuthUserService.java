package com.example.start_spring_app.service;

import com.example.start_spring_app.dto.UpdateProfileDTO;
import com.example.start_spring_app.dto.UserProfile;
import com.example.start_spring_app.dto.request.SignInrequestDTO;
import com.example.start_spring_app.dto.response.SignInResponseDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AuthUserService {
    SignInResponseDTO login(SignInrequestDTO request);
    UserProfile profile(Authentication authentication);
    void logout(HttpServletRequest request, HttpServletResponse response);
    UserResponseDTO updateProfile(UpdateProfileDTO requestDTO);
    void deleteProfile();
}
