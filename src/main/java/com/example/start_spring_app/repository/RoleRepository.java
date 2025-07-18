package com.example.start_spring_app.repository;

import com.example.start_spring_app.entities.Role;
import com.example.start_spring_app.enumType.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(RoleName roleName);
}
