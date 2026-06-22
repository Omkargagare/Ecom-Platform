package com.omkar.ecom.service;

import com.omkar.ecom.exception.InvalidTokenException;
import com.omkar.ecom.model.RefreshToken;
import com.omkar.ecom.model.Users;
import com.omkar.ecom.repository.RefreshTokenRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TokenService {

    private final RefreshTokenRepo repo;

    private final PasswordEncoder encoder;

    public TokenService(RefreshTokenRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    String generateRefreshToken(Users user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString() + UUID.randomUUID());
        refreshToken.setUser(user);
        repo.save(refreshToken);

        return refreshToken.getToken();
    }

    public String generateCsrfToken(String refreshToken) {
        RefreshToken token = repo.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        String csrfToken = UUID.randomUUID().toString();
        token.setCsrfToken(encoder.encode(csrfToken));
        repo.save(token);

        return csrfToken;
    }

    @Transactional
    public void revokeAllUserTokens(Users user) {
        repo.revokeAllActiveTokens(user);
    }
}
