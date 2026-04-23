package com.jobapp.job_application_manager.dto;

public class ApiResponse {
    private boolean success;
    private String message;

    public ApiResponse() {
    }
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
