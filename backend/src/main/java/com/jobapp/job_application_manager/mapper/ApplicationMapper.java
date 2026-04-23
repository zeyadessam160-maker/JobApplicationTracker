package com.jobapp.job_application_manager.mapper;


import com.jobapp.job_application_manager.dto.JobApplicationRequest;
import com.jobapp.job_application_manager.dto.JobApplicationResponse;
import com.jobapp.job_application_manager.entity.Application;

public class ApplicationMapper {

    // DTO → Entity
    public static Application toEntity(JobApplicationRequest request) {
        Application app = new Application();

        app.setCompanyName(request.getCompanyName());
        app.setPosition(request.getPosition());
        app.setAppliedDate(request.getAppliedDate());
        app.setAppStatus(request.getStatus());

        return app;
    }

    // Entity → Response DTO
    public static JobApplicationResponse toResponse(Application app) {
        JobApplicationResponse response = new JobApplicationResponse();

        response.setId(app.getId());
        response.setCompanyName(app.getCompanyName());
        response.setPosition(app.getPosition());
        response.setStatus(app.getAppStatus());
        response.setAppliedDate(app.getAppliedDate());

        return response;
    }

}
