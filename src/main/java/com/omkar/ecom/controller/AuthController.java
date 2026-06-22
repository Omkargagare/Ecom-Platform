package com.omkar.ecom.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import com.omkar.ecom.dto.*;
import com.omkar.ecom.exception.CsrfValidationException;
import com.omkar.ecom.exception.InvalidTokenException;
import com.omkar.ecom.model.RefreshToken;
import com.omkar.ecom.repository.RefreshTokenRepo;
import com.omkar.ecom.response.ApiResponse;
import com.omkar.ecom.service.CookieService;
import com.omkar.ecom.service.JWTService;
import com.omkar.ecom.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    private final RefreshTokenRepo refreshTokenRepo;

    private final CookieService cookieService;

    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, RefreshTokenRepo refreshTokenRepo, CookieService cookieService, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.refreshTokenRepo = refreshTokenRepo;
        this.cookieService = cookieService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerUser(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User registered successfully", null, true));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        AuthTokens tokens = userService.verify(request);

        cookieService.addRefreshToken(response, tokens.getRefreshToken());

        cookieService.addCsrfToken(response, tokens.getCsrfToken());

        return ResponseEntity.ok(new ApiResponse<>("Login successful", new LoginResponse(tokens.getAccessToken()), true));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, @CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        String accessToken = jwtService.extractTokenFromHeader(request);
        userService.logoutSession(accessToken, refreshToken);

        cookieService.removeRefreshToken(response);

        cookieService.removeCsrfToken(response);

        return ResponseEntity.ok(new ApiResponse<>("Logout", null, true));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshResponse>> refreshToken(HttpServletRequest request, HttpServletResponse response,
                                                                     @CookieValue(name = "refreshToken", required = false) String refreshTokenValue,
                                                                     @CookieValue(name = "csrfToken", required = false) String csrfTokenValue,
                                                                     @RequestHeader(value = "X-CSRF-TOKEN", required = false) String csrfHeader) {
        if (refreshTokenValue == null) {
            throw new InvalidTokenException("Missing refresh token");
        }

        RefreshToken storedToken = refreshTokenRepo.findByToken(refreshTokenValue)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        if (csrfHeader == null || !passwordEncoder.matches(csrfHeader, storedToken.getCsrfToken())) {
            throw new CsrfValidationException("CSRF Validation Failed");
        }

        AuthTokens tokens = userService.refresh(refreshTokenValue);

        cookieService.addRefreshToken(response, tokens.getRefreshToken());

        cookieService.addCsrfToken(response, tokens.getCsrfToken());

        return ResponseEntity.ok(new ApiResponse<>("Refreshed successful", new RefreshResponse(tokens.getAccessToken()), true));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        UserResponse userResponse = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>("User details retrieved", userResponse, true));
    }
}
