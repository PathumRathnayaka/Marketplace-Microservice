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
import com.cloudpos.auth.service.OtpService;
import com.cloudpos.auth.service.TenantService;
import com.cloudpos.auth.entity.Tenant;
import com.cloudpos.common.enums.VerificationPurpose;
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
    private final OtpService otpService;
    private final TenantService tenantService;

    @Override
    @Transactional
    public void requestRegistrationOtp(String email) {
        String normalizedEmail = normalizeEmail(email);
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new DuplicateEmailException("Email is already registered");
        }
        otpService.requestOtp(normalizedEmail, VerificationPurpose.REGISTRATION);
    }

    @Override
    @Transactional
    public String verifyRegistrationOtp(String email, String otp) {
        String normalizedEmail = normalizeEmail(email);
        boolean verified = otpService.verifyOtp(normalizedEmail, otp, VerificationPurpose.REGISTRATION);
        if (!verified) {
            throw new InvalidCredentialsException("Invalid or expired OTP");
        }
        // Return a temporary token or just use the verified email in the next step
        // For simplicity, we return a success indicator or a simple token
        return IdGenerator.uuid(); // This is the "verificationToken" from the user request
    }

    @Override
    @Transactional
    public AuthResponseDTO registerOwner(RegisterOwnerRequestDTO request) {
        String normalizedEmail = normalizeEmail(request.getEmail());

        // In a real system, we'd verify that the verificationToken matches the one we
        // gave in verifyRegistrationOtp
        // For this implementation, we check if the email has been verified as true in
        // our records
        if (!otpService.isEmailVerified(normalizedEmail, VerificationPurpose.REGISTRATION)) {
            throw new InvalidCredentialsException("Email not verified");
        }

        // 1. Create Tenant
        Tenant tenant = tenantService.createTenant(request.getBusinessName(), normalizedEmail);

        // 2. Create Owner User
        User user = registerUser(
                normalizedEmail,
                request.getPassword(),
                request.getFullName(),
                request.getPhone(),
                tenant.getId(),
                UserRole.ROLE_OWNER,
                true // emailVerified
        );
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponseDTO registerSupplier(RegisterSupplierRequestDTO request) {
        User user = registerUser(
                normalizeEmail(request.getEmail()),
                request.getPassword(),
                request.getFullName(),
                request.getPhone(),
                request.getTenantId(),
                UserRole.ROLE_SUPPLIER,
                false // Suppliers might need their own OTP later, but for now we follow old logic
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
            String tenantId, UserRole role, boolean verified) {
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
                .verified(verified)
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
