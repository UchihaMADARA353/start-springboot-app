package com.example.start_spring_app.utils;

import com.example.start_spring_app.entities.Role;
import com.example.start_spring_app.entities.User;
import com.example.start_spring_app.enumType.RoleName;
import com.example.start_spring_app.exception.RoleNotFoundException;
import com.example.start_spring_app.repository.RoleRepository;
import com.example.start_spring_app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class InitialiseUser {
    @Value("${spring.admin.pseudo}")
    private String username;

    @Value("${spring.admin.password}")
    private String password;

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository,
                                               PasswordEncoder passwordEncoder,
                                               UserRepository userRepository) {
        return args -> {
            // CREATION DES ROLES (ADMIN & USER)
            Role roleAdmin = Role.builder()
                    .roleName(RoleName.ADMIN)
                    .build();

            Role roleUser = Role.builder()
                    .roleName(RoleName.USER)
                    .build();
            Optional<Role> findRole = roleRepository.findByRoleName(RoleName.ADMIN);
            if (findRole.isPresent()) {
                log.info("\n\n❌ Ce role existe déjà\n");
            } else {
                roleRepository.save(roleAdmin);
                roleRepository.save(roleUser);
                log.info("\n\n✅ Ce role a été créer avec succès\n");
            }

            // INITIALISATION DE L'ADMIN
            Role role = roleRepository.findByRoleName(RoleName.ADMIN).orElseThrow(() -> new RoleNotFoundException("Role introuvable"));
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            User user = User.builder()
                    .email(username)
                    .birthDate(new Date())
                    .password(passwordEncoder.encode(password))
                    .roles(roles)
                    .name("Admin")
                    .isActived(true)
                    .build();

            Optional<User> findUser = userRepository.findByEmail("adminghis@gmail.com");
            if (findUser.isPresent()) {
                log.info("\n\n✅👍 Admin : adminghis@gmail.com\n");
            } else {
                userRepository.save(user);
                log.info("\n\n✅ Admin créer avec succès\n");
            }
        };
    }
}
