package com.example.start_spring_app.mapper;

import com.example.start_spring_app.dto.PersonalAccessTokenDTO;
import com.example.start_spring_app.entities.PersonalAccessToken;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public static PersonalAccessTokenDTO toDto(PersonalAccessToken token) {
        return PersonalAccessTokenDTO.builder()
                .id(token.getId()).email(token.getEmail()).token(token.getToken())
                .build();
    }

    public static PersonalAccessToken toEntity(PersonalAccessTokenDTO tokenDTO) {
        return PersonalAccessToken.builder()
                .id(tokenDTO.id()).email(tokenDTO.email()).token(tokenDTO.token())
                .build();
    }
}
