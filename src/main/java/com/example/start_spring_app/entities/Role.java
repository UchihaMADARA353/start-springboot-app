package com.example.start_spring_app.entities;

import com.example.start_spring_app.enumType.RoleName;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Builder @Getter @Setter
@Entity @Table(name = "roles")
public class Role extends AbstractEntity {
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
