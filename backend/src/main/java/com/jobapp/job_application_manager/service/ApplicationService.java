package com.jobapp.job_application_manager.service;
import com.jobapp.job_application_manager.dto.ApiResponse;
import com.jobapp.job_application_manager.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import com.jobapp.job_application_manager.exception.ApplicationNotFoundException;
import com.jobapp.job_application_manager.exception.BadRequestException;
import com.jobapp.job_application_manager.dto.JobApplicationRequest;
import com.jobapp.job_application_manager.dto.JobApplicationResponse;
import com.jobapp.job_application_manager.entity.*;
import java.time.LocalDateTime;
import com.jobapp.job_application_manager.mapper.ApplicationMapper;
import java.util.List;
import java.util.ArrayList;
import com.jobapp.job_application_manager.repository.UserRepository;
@Service
public class ApplicationService {
    private final  ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    public ApplicationService(ApplicationRepository applicationRepository ,  UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    // this function for creating application and saving it in database and returning response for front end
    public JobApplicationResponse createApplication(JobApplicationRequest request,String email) {

        // validation
        validateRequest(request);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // create entity
        Application app = ApplicationMapper.toEntity(request);
        app.setUser(user);
        // map entity to dto for response
        Application savedApplication = applicationRepository.save(app);

         return ApplicationMapper.toResponse(savedApplication);
    }
    // this function is responsible for listing all application of the user
    public List<JobApplicationResponse> listApplications(String email) {

        // get user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // get only this user's applications
        List<Application> applications = applicationRepository.findByUser(user);

        // map to response
        List<JobApplicationResponse> responseList = new ArrayList<>();

        for (Application app : applications) {
            responseList.add(ApplicationMapper.toResponse(app));
        }

        return responseList;
    }

    //this function is responsible for deleting application from database and sending ApiResponse to frontend
    public ApiResponse deleteApplication(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));

        if (!app.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }

        applicationRepository.delete(app);

        return new ApiResponse(true, "Application deleted successfully");
    }

    // this function is responsible for updating the application with the new values and saves it in database
    public JobApplicationResponse updateApplication(Long id, JobApplicationRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));


        if (!app.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }

        validateRequest(request);

        app.setCompanyName(request.getCompanyName());
        app.setPosition(request.getPosition());
        app.setAppliedDate(request.getAppliedDate());
        app.setAppStatus(request.getStatus());
        app.setUpdatedAt(LocalDateTime.now());

        Application updatedApp = applicationRepository.save(app);

        return ApplicationMapper.toResponse(updatedApp);
    }
    private void validateRequest(JobApplicationRequest request) {

        if (request.getCompanyName() == null || request.getCompanyName().isBlank()) {
            throw new BadRequestException("Company name is required");
        }

        if (request.getPosition() == null || request.getPosition().isBlank()) {
            throw new BadRequestException("Position is required");
        }

        if (request.getAppliedDate() == null) {
            throw new BadRequestException("Applied date is required");
        }

        if (request.getStatus() == null) {
            throw new BadRequestException("Status is required");
        }
    }
}
