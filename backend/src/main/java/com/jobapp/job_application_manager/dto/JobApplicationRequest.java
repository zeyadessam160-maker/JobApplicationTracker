package com.jobapp.job_application_manager.dto;
import  com.jobapp.job_application_manager.entity.Status;


import java.time.LocalDate;

public class JobApplicationRequest {
    private String companyName;
    private String position;
    private Status status;
    private LocalDate appliedDate;

    public JobApplicationRequest() {}

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }


    public String getPosition() {
        return position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }
}
