package com.example.start_spring_app.service.impl.auth;

import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.exception.UserNotFoundException;
import com.example.start_spring_app.repository.UserRepository;
import com.example.start_spring_app.service.ChangeEmailService;
import com.example.start_spring_app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ChangeEmailServiceImpl implements ChangeEmailService {
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Override
    public void changeEmail(Authentication authentication, String email, String newEmail) {
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            if (!Objects.equals(email, newEmail)) {
                user.setEmail(newEmail);
                userRepository.save(user);
            }
        }
    }
}
