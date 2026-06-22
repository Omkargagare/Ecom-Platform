package com.omkar.ecom.service;

import com.omkar.ecom.dto.AuthTokens;
import com.omkar.ecom.dto.LoginRequest;
import com.omkar.ecom.dto.RegisterRequest;
import com.omkar.ecom.dto.UserResponse;
import com.omkar.ecom.exception.InvalidTokenException;
import com.omkar.ecom.exception.UsernameAlreadyExistsException;
import com.omkar.ecom.model.BlacklistToken;
import com.omkar.ecom.model.RefreshToken;
import com.omkar.ecom.model.Users;
import com.omkar.ecom.repository.BlacklistTokenRepo;
import com.omkar.ecom.repository.RefreshTokenRepo;
import com.omkar.ecom.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final BlacklistTokenRepo blacklistRepo;

    private final RefreshTokenRepo refreshTokenRepo;

    private final AuthenticationManager authManager;

    private final JWTService jwtService;

    private final PasswordEncoder encoder;

    private final TokenService tokenService;

    public UserService(UserRepo userRepo, BlacklistTokenRepo blacklistRepo, RefreshTokenRepo refreshTokenRepo, AuthenticationManager authManager, JWTService jwtService, PasswordEncoder encoder, TokenService tokenService) {
        this.userRepo = userRepo;
        this.blacklistRepo = blacklistRepo;
        this.refreshTokenRepo = refreshTokenRepo;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    public void registerUser(RegisterRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));

        userRepo.save(user);
    }

    public AuthTokens verify(LoginRequest request) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String accessToken = jwtService.generateAccessToken(authentication.getName());

        Users user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String refreshToken = tokenService.generateRefreshToken(user);
        String csrfToken = tokenService.generateCsrfToken(refreshToken);

        return new AuthTokens(accessToken, refreshToken, csrfToken);
    }

    public void logoutSession(String accessToken, String refreshToken) {

        if (accessToken != null) {
            try {
                String jti = jwtService.extractJtiFromToken(accessToken);
                Instant expiryTime = jwtService.extractExpirationInstant(accessToken);

                blacklistRepo.save(new BlacklistToken(jti, expiryTime));
            } catch (Exception ignored) {
                //Intentionally Ignored
            }
        }

        if (refreshToken != null) {
            refreshTokenRepo.findByToken(refreshToken)
                    .ifPresent(token -> {
                        token.setRevoked(true);
                        token.setCsrfToken(null);
                        refreshTokenRepo.save(token);
                    });
        }
    }

    public AuthTokens refresh(String refreshToken) {
        RefreshToken token = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        Users user = token.getUser();

        if (token.isRevoked()) {
            tokenService.revokeAllUserTokens(user);
            throw new InvalidTokenException("Invalid refresh token");
        }

        if (token.isExpired()) throw new InvalidTokenException("Refresh token expired");

        token.setRevoked(true);
        refreshTokenRepo.save(token);

        String newAccessToken = jwtService.generateAccessToken(user.getUsername());

        String newRefreshToken = tokenService.generateRefreshToken(user);

        String newCsrfToken = tokenService.generateCsrfToken(newRefreshToken);

        return new AuthTokens(newAccessToken, newRefreshToken, newCsrfToken);
    }

    public UserResponse getCurrentUser(String username) {
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getRole().name());
    }
}
