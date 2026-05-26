package com.cloudpos.auth.service.impl;

import com.cloudpos.auth.entity.RefreshToken;
import com.cloudpos.auth.entity.User;
import com.cloudpos.auth.exception.TokenException;
import com.cloudpos.auth.repository.RefreshTokenRepository;
import com.cloudpos.auth.service.JwtService;
import com.cloudpos.auth.service.RefreshTokenService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${app.jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        String token = jwtService.generateRefreshToken(user);
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(user.getId())
                .token(token)
                .expiryDate(Instant.now().plusMillis(refreshTokenExpirationMs))
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public RefreshToken validateRefreshToken(String refreshToken) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new TokenException("Refresh token not found"));

        if (Boolean.TRUE.equals(storedToken.getRevoked())) {
            throw new TokenException("Refresh token has been revoked");
        }

        if (storedToken.getExpiryDate().isBefore(Instant.now())) {
            storedToken.setRevoked(true);
            refreshTokenRepository.save(storedToken);
            throw new TokenException("Refresh token has expired");
        }

        jwtService.validateToken(refreshToken);
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new TokenException("Expected refresh token");
        }

        return storedToken;
    }

    @Override
    @Transactional
    public void revokeRefreshToken(String refreshToken) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new TokenException("Refresh token not found"));
        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);
    }
}
