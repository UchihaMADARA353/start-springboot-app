package com.example.start_spring_app.service;

import com.example.start_spring_app.dto.PersonalAccessTokenDTO;
import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.entities.PersonalAccessToken;

public interface RegisterUserService {
    UserResponseDTO registerUser(UserRequestDTO userDTO);
    void confirmAccount(PersonalAccessTokenDTO accessToken);
    void generateToken(String email);
}
