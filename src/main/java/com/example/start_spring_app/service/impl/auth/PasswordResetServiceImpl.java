package com.example.start_spring_app.service.impl.auth;

import com.example.start_spring_app.dto.MailInfo;
import com.example.start_spring_app.entities.PersonalAccessToken;
import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.exception.UserNotFoundException;
import com.example.start_spring_app.repository.PersonalAccessTokenRepository;
import com.example.start_spring_app.repository.UserRepository;
import com.example.start_spring_app.service.EmailService;
import com.example.start_spring_app.service.PasswordResetService;
import com.example.start_spring_app.utils.TokenEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final PersonalAccessTokenRepository personalAccessTokenRepository;
    private final EmailService emailService;

    @Override
    public void forgetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (user.isActived()) {
            MailInfo info = MailInfo.builder()
                    .sendTo(email).name(user.getName()).subject("Réinitialisation du mot de passe")
                    .build();
            emailService.generate(info);
            log.info("\n\nUne email vous a été envoyé");
        }
    }

    @Override
    public void resetPassword(String email, String token, String password, String passwordConfirmation) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        PersonalAccessToken accessToken = personalAccessTokenRepository
                .findByEmail(email);
        try {
            if (Objects.equals(email, user.getEmail()) && TokenEncryptor.decrypt(token).equals(accessToken.getToken())) {
                if (Objects.equals(password, passwordConfirmation)) {
                    User savedUser = User.builder()
                            .password(password)
                            .build();
                    userRepository.save(savedUser);
                } else {
                    throw new RuntimeException("Les mots de passes ne correspondent pas");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error" + e);
        }
    }
}
