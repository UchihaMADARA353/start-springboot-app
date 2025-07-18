package com.example.start_spring_app.service.impl.auth;

import com.example.start_spring_app.dto.UserProfile;
import com.example.start_spring_app.dto.request.SignInrequestDTO;
import com.example.start_spring_app.dto.request.UserRequestDTO;
import com.example.start_spring_app.dto.response.SignInResponseDTO;
import com.example.start_spring_app.dto.response.UserResponseDTO;
import com.example.start_spring_app.entities.Role;
import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.enumType.RoleName;
import com.example.start_spring_app.exception.UserNotAuthorizedException;
import com.example.start_spring_app.exception.UserNotFoundException;
import com.example.start_spring_app.mapper.RoleMapper;
import com.example.start_spring_app.mapper.UserMapper;
import com.example.start_spring_app.repository.UserRepository;
import com.example.start_spring_app.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public SignInResponseDTO login(SignInrequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        //*********************************//
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        if (!user.isActived()) {
            throw new UserNotAuthorizedException("Vous n'êtes pas autorisé");
        }
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = jwtService.generateToken(authentication);
        log.info("\n\n✅😊 Authentification réussie.\n");
        log.info("\n🔑 Token : {}", token + "\n\n");
        return SignInResponseDTO.builder()
                .username(authentication.getName())
                .token(token)
                .roles(roles)
                .build();
    }

    @Override
    public UserProfile profile(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

        List<RoleName> roles = user.getRoles().stream().map(Role::getRoleName).toList();

        return UserProfile.builder()
                .email(user.getEmail()).roles(roles).isAuthenticatd(authentication.isAuthenticated())
                .isActived(user.isActived()).birthDate(user.getBirthDate())
                .name(user.getName())
                .build();
    }

    @Override
    public UserResponseDTO updateProfile(UserRequestDTO requestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotAuthorizedException("Vous n'êtes pas autorisé");
        }
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));
        user.setBirthDate(requestDTO.birthDate());
        User updateUser = userRepository.save(user);
        return UserMapper.toDto(updateUser);
    }

    @Override
    public void deleteProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotAuthorizedException("Vous n'êtes pas autorisé");
        }
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));
        userRepository.delete(user);
    }
}
