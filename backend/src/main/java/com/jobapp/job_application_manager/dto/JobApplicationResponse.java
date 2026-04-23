package com.jobapp.job_application_manager.dto;
import  com.jobapp.job_application_manager.entity.Status;

import java.time.LocalDate;

public class JobApplicationResponse {
    private Long id;
    private String companyName;
    private String position;
    private Status status;
    private LocalDate appliedDate;

    public JobApplicationResponse() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPosition() {
        return position;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public Status getStatus() {
        return status;
    }
}
