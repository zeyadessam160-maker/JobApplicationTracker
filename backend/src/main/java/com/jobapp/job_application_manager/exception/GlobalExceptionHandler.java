package com.jobapp.job_application_manager.exception;

import com.jobapp.job_application_manager.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle specific exception (Application not found)
    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ApiResponse> handleApplicationNotFound(ApplicationNotFoundException ex) {

        ApiResponse response = new ApiResponse(false, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

   // handling specific exception ( bad requests )
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequest(BadRequestException ex) {

        ApiResponse response = new ApiResponse(false, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle all other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {

        ApiResponse response = new ApiResponse(false, "Something went wrong");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
