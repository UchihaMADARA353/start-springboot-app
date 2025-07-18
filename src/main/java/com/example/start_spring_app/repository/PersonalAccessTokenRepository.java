package com.example.start_spring_app.repository;

import com.example.start_spring_app.entities.PersonalAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalAccessTokenRepository extends JpaRepository<PersonalAccessToken, Long> {
    PersonalAccessToken findByEmail(String email);
}
