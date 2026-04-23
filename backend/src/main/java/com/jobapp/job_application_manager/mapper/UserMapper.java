package com.jobapp.job_application_manager.mapper;

import com.jobapp.job_application_manager.dto.RegisterRequest;
import com.jobapp.job_application_manager.entity.User;

public class UserMapper {
    public static User toEntity(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // later we hash it
        return user;
    }
}
