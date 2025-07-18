package com.example.start_spring_app.service;

import com.example.start_spring_app.dto.MailInfo;

public interface EmailService {
    void generate(MailInfo mailInfo);
}
