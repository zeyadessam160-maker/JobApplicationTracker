package com.jobapp.job_application_manager.controller;


import com.jobapp.job_application_manager.dto.ApiResponse;
import com.jobapp.job_application_manager.dto.JobApplicationRequest;
import com.jobapp.job_application_manager.dto.JobApplicationResponse;
import com.jobapp.job_application_manager.service.ApplicationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public JobApplicationResponse createApplication(@RequestBody JobApplicationRequest request) {
        return applicationService.createApplication(request);
    }

    @GetMapping
    public List<JobApplicationResponse> listApplications() {
        return applicationService.listApplications();
    }

    @PutMapping("/{id}")
    public JobApplicationResponse updateApplication(
            @PathVariable Long id,
            @RequestBody JobApplicationRequest request) {

        return applicationService.updateApplication(id, request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteApplication(@PathVariable Long id) {
        return applicationService.deleteApplication(id);
    }
}

