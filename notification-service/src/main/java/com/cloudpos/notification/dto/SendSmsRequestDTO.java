package com.cloudpos.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsRequestDTO {

    private String notificationId;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Size(max = 2000)
    private String message;
}
