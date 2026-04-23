package com.jobapp.job_application_manager.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status appStatus;

    @Column(nullable = false)
    private LocalDate appliedDate;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public Application(){

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
    public Status getAppStatus() {
        return appStatus;
    }
    public LocalDate getAppliedDate() {
        return appliedDate;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public User getUser() {
        return user;
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
    public void setAppStatus(Status appStatus) {
        this.appStatus = appStatus;
    }
    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setUser(User user) {
        this.user = user;
    }
}

