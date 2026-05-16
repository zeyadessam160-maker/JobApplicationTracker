package com.jobapp.job_application_manager.controller;
import com.jobapp.job_application_manager.dto.*;
import com.jobapp.job_application_manager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jobapp.job_application_manager.security.JwtService;
import com.jobapp.job_application_manager.exception.*;




@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService,JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ChangePasswordRequest request) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (email == null) {
            throw new BadRequestException("Invalid or expired token");
        }

        return ResponseEntity.ok(authService.changePassword(email, request));
    }
}
