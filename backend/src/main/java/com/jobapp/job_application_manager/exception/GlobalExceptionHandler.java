package com.jobapp.job_application_manager.exception;

import com.jobapp.job_application_manager.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle specific exception (Application not found)
    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ApiResponse> handleApplicationNotFound(ApplicationNotFoundException ex) {

        ApiResponse response = new ApiResponse(false, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

   // handling specific exception ( bad requests )
   @ExceptionHandler(Exception.class)
   public ResponseEntity<ApiResponse> handleGeneralException(Exception ex, HttpServletRequest request) {

       String path = request.getRequestURI();

       if (path.startsWith("/h2-console")) {
           throw new RuntimeException(ex);
       }

       ApiResponse response = new ApiResponse(false, "Something went wrong");

       return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
   }

}
