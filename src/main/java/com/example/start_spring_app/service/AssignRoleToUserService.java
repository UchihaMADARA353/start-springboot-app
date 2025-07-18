package com.example.start_spring_app.service;

import com.example.start_spring_app.enumType.RoleName;

public interface AssignRoleToUserService {
    void assignRoleToUser(String email, RoleName roleName);
}
