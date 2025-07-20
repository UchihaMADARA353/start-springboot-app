package com.example.start_spring_app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class MailInfo {
    private String sendTo;
    private String subject;

    // USERS INFOS
    private String name;
    private String email;
    private String password;
}
