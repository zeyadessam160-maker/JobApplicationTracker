package com.jobapp.job_application_manager.service;

import com.jobapp.job_application_manager.dto.ProfileResponse;
import com.jobapp.job_application_manager.dto.ProfileUpdateRequest;
import com.jobapp.job_application_manager.entity.User;
import com.jobapp.job_application_manager.repository.UserRepository;
import org.springframework.stereotype.Service;



@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProfileResponse res = new ProfileResponse();
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setProfileImage(user.getProfileImage());

        return res;
    }


    public ProfileResponse updateProfile(String email, ProfileUpdateRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }

        if (request.getProfileImage() != null) {
            user.setProfileImage(request.getProfileImage());
        }

        userRepository.save(user);

        ProfileResponse res = new ProfileResponse();
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setProfileImage(user.getProfileImage());

        return res;
    }
}
