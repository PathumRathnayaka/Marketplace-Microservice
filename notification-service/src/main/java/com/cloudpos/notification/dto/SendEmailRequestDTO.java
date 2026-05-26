package com.cloudpos.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequestDTO {

    private String notificationId;

    @Email
    @NotBlank
    private String recipientEmail;

    @NotBlank
    @Size(max = 255)
    private String subject;

    @NotBlank
    @Size(max = 8000)
    private String body;

    private boolean html;
    private String templateName;
    private Map<String, Object> templateVariables;
}
