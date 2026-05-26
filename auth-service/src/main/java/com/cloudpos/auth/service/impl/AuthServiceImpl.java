package com.cloudpos.auth.service.impl;

import com.cloudpos.auth.dto.AuthResponseDTO;
import com.cloudpos.auth.dto.LoginRequestDTO;
import com.cloudpos.auth.dto.RegisterOwnerRequestDTO;
import com.cloudpos.auth.dto.RegisterSupplierRequestDTO;
import com.cloudpos.auth.dto.TokenValidationResponseDTO;
import com.cloudpos.auth.dto.UserResponseDTO;
import com.cloudpos.auth.entity.RefreshToken;
import com.cloudpos.auth.entity.User;
import com.cloudpos.auth.exception.DuplicateEmailException;
import com.cloudpos.auth.exception.InvalidCredentialsException;
import com.cloudpos.auth.exception.TokenException;
import com.cloudpos.auth.mapper.UserMapper;
import com.cloudpos.auth.repository.UserRepository;
import com.cloudpos.auth.role.UserRole;
import com.cloudpos.auth.service.AuthService;
import com.cloudpos.auth.service.JwtService;
import com.cloudpos.auth.service.RefreshTokenService;
import com.cloudpos.auth.service.UserService;
import com.cloudpos.auth.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_TYPE = "Bearer";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponseDTO registerOwner(RegisterOwnerRequestDTO request) {
        User user = registerUser(
                request.getEmail(),
                request.getPassword(),
                request.getFullName(),
                request.getPhone(),
                StringUtils.hasText(request.getTenantId()) ? request.getTenantId() : IdGenerator.uuid(),
                UserRole.ROLE_OWNER
        );
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponseDTO registerSupplier(RegisterSupplierRequestDTO request) {
        User user = registerUser(
                request.getEmail(),
                request.getPassword(),
                request.getFullName(),
                request.getPhone(),
                request.getTenantId(),
                UserRole.ROLE_SUPPLIER
        );
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponseDTO login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(normalizeEmail(request.getEmail()), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userService.findActiveByEmail(normalizeEmail(request.getEmail()));
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponseDTO refreshAccessToken(String refreshToken) {
        RefreshToken storedRefreshToken = refreshTokenService.validateRefreshToken(refreshToken);
        User user = userRepository.findById(storedRefreshToken.getUserId())
                .filter(value -> Boolean.TRUE.equals(value.getActive()))
                .orElseThrow(() -> new TokenException("Refresh token user is not available"));

        return AuthResponseDTO.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(refreshToken)
                .tokenType(TOKEN_TYPE)
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.revokeRefreshToken(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO currentUser(String email) {
        return userMapper.toResponse(userService.findActiveByEmail(email));
    }

    @Override
    @Transactional(readOnly = true)
    public TokenValidationResponseDTO validateAccessToken(String accessToken) {
        boolean valid = jwtService.validateToken(accessToken) && !jwtService.isRefreshToken(accessToken);
        return TokenValidationResponseDTO.builder()
                .valid(valid)
                .email(jwtService.extractEmail(accessToken))
                .role(jwtService.extractRole(accessToken))
                .build();
    }

    private User registerUser(String email, String rawPassword, String fullName, String phone,
                              String tenantId, UserRole role) {
        String normalizedEmail = normalizeEmail(email);
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new DuplicateEmailException("Email is already registered");
        }

        User user = User.builder()
                .email(normalizedEmail)
                .password(passwordEncoder.encode(rawPassword))
                .fullName(fullName)
                .phone(phone)
                .role(role)
                .tenantId(tenantId)
                .active(true)
                .verified(false)
                .deleted(false)
                .build();

        return userRepository.save(user);
    }

    private AuthResponseDTO buildAuthResponse(User user) {
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return AuthResponseDTO.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(refreshToken.getToken())
                .tokenType(TOKEN_TYPE)
                .user(userMapper.toResponse(user))
                .build();
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
