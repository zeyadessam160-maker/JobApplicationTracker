package com.jobapp.job_application_manager.service;
import com.jobapp.job_application_manager.dto.ApiResponse;
import com.jobapp.job_application_manager.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import com.jobapp.job_application_manager.exception.ApplicationNotFoundException;
import com.jobapp.job_application_manager.exception.BadRequestException;
import com.jobapp.job_application_manager.dto.JobApplicationRequest;
import com.jobapp.job_application_manager.dto.JobApplicationResponse;
import com.jobapp.job_application_manager.entity.Application;
import java.time.LocalDateTime;
import com.jobapp.job_application_manager.mapper.ApplicationMapper;
import java.util.List;
import java.util.ArrayList;
@Service
public class ApplicationService {
    private final  ApplicationRepository applicationRepository;
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // this function for creating application and saving it in database and returning response for front end
    public JobApplicationResponse createApplication(JobApplicationRequest request) {

        // validation
        validateRequest(request);

        // create entity
        Application app = ApplicationMapper.toEntity(request);

        // map entity to dto for response
        Application savedApplication = applicationRepository.save(app);

         return ApplicationMapper.toResponse(savedApplication);
    }

    //this function is responsible for deleting application from database and sending ApiResponse to frontend
    public ApiResponse deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new ApplicationNotFoundException("Application not found");
        }
        applicationRepository.deleteById(id);
        return new ApiResponse(true,"Application deleted successfully");
    }

    // this function is responsible for updating the application with the new values and saves it in database
    public JobApplicationResponse updateApplication(Long id, JobApplicationRequest request) {

        // find the application by id
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));

        validateRequest(request);
        // update fields from request
        app.setCompanyName(request.getCompanyName());
        app.setPosition(request.getPosition());
        app.setAppliedDate(request.getAppliedDate());
        app.setAppStatus(request.getStatus());
        app.setUpdatedAt(LocalDateTime.now());

        // save updated application in database
        Application updatedApp = applicationRepository.save(app);

        return ApplicationMapper.toResponse(updatedApp);
    }

    // this method is responsible for getting all applications listed for the user in database and send it to front end
    public List<JobApplicationResponse> listApplications() {

        //  get all applications from database
        List<Application> applications = applicationRepository.findAll();

        //  create list for response
        List<JobApplicationResponse> responseList = new ArrayList<>();

        // convert each entity to dto response
        for (Application app : applications) {
            responseList.add(ApplicationMapper.toResponse(app));
        }

        return responseList;
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
