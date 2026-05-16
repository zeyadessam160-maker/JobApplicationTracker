package com.jobapp.job_application_manager.service;

import com.jobapp.job_application_manager.entity.User;
import com.jobapp.job_application_manager.repository.UserRepository;
import com.jobapp.job_application_manager.security.CustomUserDetails;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        return new CustomUserDetails(user);
    }
}