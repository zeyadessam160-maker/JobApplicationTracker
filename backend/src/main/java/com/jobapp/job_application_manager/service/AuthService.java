package com.jobapp.job_application_manager.service;

import com.jobapp.job_application_manager.dto.*;
import com.jobapp.job_application_manager.entity.User;
import com.jobapp.job_application_manager.mapper.UserMapper;
import com.jobapp.job_application_manager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jobapp.job_application_manager.security.JwtService;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository ,  PasswordEncoder passwordEncoder ,  JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // REGISTER
    public ApiResponse register(RegisterRequest request) {

        // check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // map DTO to entity
        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // save user
        userRepository.save(user);

        return new ApiResponse(true, "User registered successfully");
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        // find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, user.getName(), user.getEmail());
    }
}
