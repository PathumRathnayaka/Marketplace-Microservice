package com.cloudpos.auth.controller;

import com.cloudpos.auth.dto.ApiResponse;
import com.cloudpos.auth.dto.AuthResponseDTO;
import com.cloudpos.auth.dto.LoginRequestDTO;
import com.cloudpos.auth.dto.LogoutRequestDTO;
import com.cloudpos.auth.dto.RefreshTokenRequestDTO;
import com.cloudpos.auth.dto.RegisterOwnerRequestDTO;
import com.cloudpos.auth.dto.RegisterSupplierRequestDTO;
import com.cloudpos.auth.dto.TokenValidationRequestDTO;
import com.cloudpos.auth.dto.TokenValidationResponseDTO;
import com.cloudpos.auth.dto.UserResponseDTO;
import com.cloudpos.auth.exception.UnauthorizedException;
import com.cloudpos.auth.security.CustomUserDetailsService;
import com.cloudpos.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-owner")
    @Operation(summary = "Register a shop owner")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> registerOwner(
            @Valid @RequestBody RegisterOwnerRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Owner registration successful",
                authService.registerOwner(request)));
    }

    @PostMapping("/register-supplier")
    @Operation(summary = "Register a supplier")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> registerSupplier(
            @Valid @RequestBody RegisterSupplierRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Supplier registration successful",
                authService.registerSupplier(request)));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Login successful",
                authService.login(request)));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Generate a new access token from a refresh token")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refresh(@Valid @RequestBody RefreshTokenRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Token refreshed successfully",
                authService.refreshAccessToken(request.getRefreshToken())));
    }

    @PostMapping("/logout")
    @Operation(summary = "Revoke a refresh token")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody LogoutRequestDTO request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }

    @GetMapping("/me")
    @Operation(summary = "Return the authenticated user's profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> me(
            @AuthenticationPrincipal CustomUserDetailsService.AuthUserDetails principal) {
        if (principal == null) {
            throw new UnauthorizedException("Authentication is required");
        }

        return ResponseEntity.ok(ApiResponse.success(
                "Current user fetched successfully",
                authService.currentUser(principal.getUsername())));
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate an access token for downstream services")
    public ResponseEntity<ApiResponse<TokenValidationResponseDTO>> validate(
            @Valid @RequestBody TokenValidationRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Token validation completed",
                authService.validateAccessToken(request.getAccessToken())));
    }
}
