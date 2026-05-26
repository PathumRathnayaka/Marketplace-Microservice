package com.cloudpos.common.event;

import com.cloudpos.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEventDTO {

    private String userId;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
}
