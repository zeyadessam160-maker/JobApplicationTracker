package com.jobapp.job_application_manager.controller;

import com.jobapp.job_application_manager.dto.ProfileResponse;
import com.jobapp.job_application_manager.dto.ProfileUpdateRequest;
import com.jobapp.job_application_manager.security.JwtService;
import com.jobapp.job_application_manager.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final JwtService jwtService;

    public ProfileController(ProfileService profileService, JwtService jwtService) {
        this.profileService = profileService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ProfileResponse getProfile(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return profileService.getProfile(email);
    }

    @PutMapping
    public ProfileResponse updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProfileUpdateRequest request) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return profileService.updateProfile(email, request);
    }
}
