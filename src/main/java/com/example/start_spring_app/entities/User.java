package com.example.start_spring_app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Builder @Getter @Setter
@Entity @Table(name = "users")
public class User extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = true)
    private String photo;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    private boolean isActived;
}
