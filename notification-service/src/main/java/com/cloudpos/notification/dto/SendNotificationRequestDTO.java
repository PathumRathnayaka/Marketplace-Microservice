package com.cloudpos.notification.dto;

import com.cloudpos.notification.notification.NotificationChannel;
import com.cloudpos.notification.notification.NotificationPriority;
import com.cloudpos.notification.notification.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequestDTO {

    @NotBlank
    private String userId;

    private String tenantId;

    @NotBlank
    private String recipient;

    @NotBlank
    private String recipientType;

    @NotNull
    private NotificationType notificationType;

    @NotNull
    private NotificationChannel channel;

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Size(max = 4000)
    private String message;

    private NotificationPriority priority;
    private String referenceId;
}
