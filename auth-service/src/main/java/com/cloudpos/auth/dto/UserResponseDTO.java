package com.cloudpos.auth.dto;

import com.cloudpos.auth.role.UserRole;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDTO {

    private final String id;
    private final String email;
    private final String fullName;
    private final String phone;
    private final UserRole role;
    private final String tenantId;
    private final Boolean isActive;
    private final Boolean isVerified;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
