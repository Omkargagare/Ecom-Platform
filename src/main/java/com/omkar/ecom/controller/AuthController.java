package com.omkar.ecom.controller;

import jakarta.validation.Valid;
import com.omkar.ecom.dto.LoginRequest;
import com.omkar.ecom.dto.LoginResponse;
import com.omkar.ecom.dto.RegisterRequest;
import com.omkar.ecom.response.ApiResponse;
import com.omkar.ecom.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerUser(@Valid @RequestBody RegisterRequest request) {
        service.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User registered successfully", null, true));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = service.verify(request);
        return ResponseEntity.ok(new ApiResponse<>("Login successful", response, true));
    }
}
