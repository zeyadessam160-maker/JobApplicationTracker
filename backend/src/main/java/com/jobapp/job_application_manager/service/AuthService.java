package com.jobapp.job_application_manager.service;

import com.jobapp.job_application_manager.dto.*;
import com.jobapp.job_application_manager.entity.User;
import com.jobapp.job_application_manager.mapper.UserMapper;
import com.jobapp.job_application_manager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jobapp.job_application_manager.security.JwtService;
import com.jobapp.job_application_manager.exception.BadRequestException;



@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // REGISTER
    public ApiResponse register(RegisterRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new BadRequestException("Email and password are required");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return new ApiResponse(true, "User registered successfully");
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new BadRequestException("Email and password are required");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, user.getName(), user.getEmail());
    }
    // change password
    public ApiResponse changePassword(String email, ChangePasswordRequest request) {

        if (request.getOldPassword() == null || request.getNewPassword() == null) {
            throw new BadRequestException("Old and new password are required");
        }

        if (request.getNewPassword().length() < 6) {
            throw new BadRequestException("New password must be at least 6 characters");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("New password must be different from old password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return new ApiResponse(true, "Password updated successfully");
    }
}