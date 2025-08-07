package com.example.start_spring_app.service.impl.auth;

import com.example.start_spring_app.dto.MailInfo;
import com.example.start_spring_app.dto.PersonalAccessTokenDTO;
import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.entities.PersonalAccessToken;
import com.example.start_spring_app.entities.Role;
import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.enumType.RoleName;
import com.example.start_spring_app.exception.UserAlreadyExistException;
import com.example.start_spring_app.exception.UserNotFoundException;
import com.example.start_spring_app.mapper.TokenMapper;
import com.example.start_spring_app.mapper.UserMapper;
import com.example.start_spring_app.repository.PersonalAccessTokenRepository;
import com.example.start_spring_app.repository.RoleRepository;
import com.example.start_spring_app.repository.UserRepository;
import com.example.start_spring_app.service.EmailService;
import com.example.start_spring_app.service.RegisterUserService;
import com.example.start_spring_app.utils.TokenEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class RegisterUserServiceImpl implements RegisterUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final EmailService emailService;
    private final PersonalAccessTokenRepository personalAccessTokenRepository;

    @Transactional
    @Override
    public UserResponseDTO registerUser(UserRequestDTO userDTO) {
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByRoleName(RoleName.USER)
            .orElseThrow(() -> new IllegalArgumentException("Role not found: " + RoleName.USER));
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new UserAlreadyExistException("Email already exists");
        }
        if (!Objects.equals(userDTO.passwordConfirmation(), userDTO.password())) {
            throw new IllegalArgumentException("Password confirmation does not match");
        }
        roles.add(role);
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setRoles(roles);
        user.setActived(false);
        // ENREGISTER L'UTILISATEUR 🧑‍💼
        User savedUser = userRepository.save(user);
        // ENVOYER UN MAIL DE CONFIRMATION
        MailInfo mailInfo = MailInfo.builder()
                .sendTo(userDTO.email()).subject("Code d'activation du compte")
                .name(user.getName())
                .build();
        emailService.generate(mailInfo);
        log.info("\n\n✅ Code d'activation du compte envoyer avec succès");
        PersonalAccessToken accessToken = personalAccessTokenRepository.findByEmail(userDTO.email());

        scheduleDeleteToken(accessToken);
        scheduleDeletionIfInactive(savedUser);

        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public void confirmAccount(PersonalAccessTokenDTO accessToken) {
        PersonalAccessToken findToken = personalAccessTokenRepository
                .findByEmail(accessToken.email());

        PersonalAccessToken token = TokenMapper.toEntity(accessToken);
        try {
            if (Objects.equals(token.getEmail(), findToken.getEmail())
                    && Objects.equals(accessToken.token(), TokenEncryptor.decrypt(findToken.getToken()))) {
                User user = userRepository.findByEmail(token.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
                user.setActived(true);
                userRepository.save(user);
                personalAccessTokenRepository.delete(token);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error de confirmaton du compte");
        }

    }

    @Override
    public void generateToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        if (user != null) {
            PersonalAccessToken personalAccessToken = personalAccessTokenRepository
                    .findByEmail(user.getEmail());
            if (personalAccessToken == null) {
                try {
                    MailInfo info = MailInfo.builder()
                            .name(user.getName()).sendTo(email).subject("Activation de compte")
                            .build();
                    emailService.generate(info);
                } catch (Exception e) {
                    throw new RuntimeException("Erreur");
                }
            }
            scheduleDeleteToken(personalAccessToken);
        }
    }

    private void scheduleDeletionIfInactive(User user) {
        scheduler.schedule(() -> {
            User u = userRepository.findById(user.getId()).orElse(null);
            if (u != null && !u.isActived()) {
                userRepository.delete(u);
                log.info("\n\n❌ Utilisateur {} supprimé après 5 min car toujours inactif.", u.getId());
            } else {
                log.info("\n\n✅ Utilisateur {} est actif, pas supprimé.", user.getId());
            }
        }, 1440, TimeUnit.MINUTES);
    }

    private void scheduleDeleteToken(PersonalAccessToken token) {
        scheduler.schedule(() -> {
           Optional<PersonalAccessToken> accessToken = personalAccessTokenRepository.findById(token.getId());
           if (accessToken.isPresent()) {
               personalAccessTokenRepository.delete(accessToken.get());
               log.info("\n\nToken supprimer");
           }
        }, 5, TimeUnit.MINUTES);
    }
}
