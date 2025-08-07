package com.example.start_spring_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(RoleNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<String> usernameNotAuthorized(Exception exception) {
        return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePhotoNotFound(FileNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Photo not found");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
