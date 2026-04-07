package com.omkar.ecom.service;

import com.omkar.ecom.model.RefreshToken;
import com.omkar.ecom.repository.RefreshTokenRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    private final RefreshTokenRepo repo;

    public TokenService(RefreshTokenRepo repo) {
        this.repo = repo;
    }

    String generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString() + UUID.randomUUID());
        repo.save(refreshToken);

        return refreshToken.getToken();
    }

    public String generateJti() {
        return UUID.randomUUID().toString();
    }
}
