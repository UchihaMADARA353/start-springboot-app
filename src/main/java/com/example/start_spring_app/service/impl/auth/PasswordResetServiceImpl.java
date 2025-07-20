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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final PersonalAccessTokenRepository personalAccessTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

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

        PersonalAccessToken accessToken = personalAccessTokenRepository.findByEmail(email);
        if (accessToken == null) {
            throw new RuntimeException("No valid token found for this user.");
        }

        try {
            if (!user.getEmail().equals(email)) {
                throw new RuntimeException("Email does not match.");
            }

            String decryptedToken = TokenEncryptor.decrypt(token);
            if (!decryptedToken.equals(accessToken.getToken())) {
                throw new RuntimeException("Invalid token provided.");
            }

            if (!password.equals(passwordConfirmation)) {
                throw new RuntimeException("Passwords do not match.");
            }

            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);

            // Supprime le token utilisé pour éviter réutilisation
            personalAccessTokenRepository.delete(accessToken);

            log.info("✅ Password successfully reset for user: {}", email);

        } catch (Exception e) {
            // Révéler l’erreur réelle pour debug
            throw new RuntimeException("Error while resetting password: " + e.getMessage(), e);
        }
    }

}
