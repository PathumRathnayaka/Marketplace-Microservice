package com.cloudpos.auth.dto;

import com.cloudpos.auth.role.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenValidationResponseDTO {

    private final boolean valid;
    private final String email;
    private final UserRole role;
}
