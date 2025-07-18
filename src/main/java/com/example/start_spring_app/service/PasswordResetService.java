package com.example.start_spring_app.service;

public interface PasswordResetService {
    void forgetPassword(String email);
    void resetPassword(String email, String token, String password, String passwordConfirmation);
}
