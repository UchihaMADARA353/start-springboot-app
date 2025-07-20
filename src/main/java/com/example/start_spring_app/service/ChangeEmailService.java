package com.example.start_spring_app.service;

import org.springframework.security.core.Authentication;

public interface ChangeEmailService {
    void changeEmail(Authentication authentication, String email, String newEmail);
}
