package com.cloudpos.auth.service;

import com.cloudpos.auth.entity.User;
import com.cloudpos.auth.role.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    boolean validateToken(String token);

    boolean validateToken(String token, UserDetails userDetails);

    String extractEmail(String token);

    UserRole extractRole(String token);

    boolean isRefreshToken(String token);
}
