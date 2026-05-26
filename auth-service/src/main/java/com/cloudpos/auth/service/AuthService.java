package com.cloudpos.auth.service;

import com.cloudpos.auth.dto.AuthResponseDTO;
import com.cloudpos.auth.dto.LoginRequestDTO;
import com.cloudpos.auth.dto.RegisterOwnerRequestDTO;
import com.cloudpos.auth.dto.RegisterSupplierRequestDTO;
import com.cloudpos.auth.dto.TokenValidationResponseDTO;
import com.cloudpos.auth.dto.UserResponseDTO;

public interface AuthService {

    AuthResponseDTO registerOwner(RegisterOwnerRequestDTO request);

    AuthResponseDTO registerSupplier(RegisterSupplierRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    AuthResponseDTO refreshAccessToken(String refreshToken);

    void logout(String refreshToken);

    UserResponseDTO currentUser(String email);

    TokenValidationResponseDTO validateAccessToken(String accessToken);
}
