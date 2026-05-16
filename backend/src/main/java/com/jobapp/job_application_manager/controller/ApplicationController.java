package com.jobapp.job_application_manager.controller;


import com.jobapp.job_application_manager.dto.ApiResponse;
import com.jobapp.job_application_manager.dto.JobApplicationRequest;
import com.jobapp.job_application_manager.dto.JobApplicationResponse;
import com.jobapp.job_application_manager.service.ApplicationService;
import com.jobapp.job_application_manager.security.JwtService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final JwtService jwtService;

    public ApplicationController(ApplicationService applicationService , JwtService jwtService) {
        this.applicationService = applicationService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public JobApplicationResponse createApplication(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody JobApplicationRequest request) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return applicationService.createApplication(request, email);
    }
    @GetMapping
    public List<JobApplicationResponse> listApplications(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return applicationService.listApplications(email);
    }

    @PutMapping("/{id}")
    public JobApplicationResponse updateApplication(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody JobApplicationRequest request) {

        String email = jwtService.extractEmail(authHeader.substring(7));

        return applicationService.updateApplication(id, request, email);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteApplication(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        String email = jwtService.extractEmail(authHeader.substring(7));

        return applicationService.deleteApplication(id, email);
    }
}

