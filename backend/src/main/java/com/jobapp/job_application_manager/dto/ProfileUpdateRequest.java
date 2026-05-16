package com.jobapp.job_application_manager.dto;

public class ProfileUpdateRequest {
    public String name;
    public String profileImage;

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getName() {
        return name;
    }
}
