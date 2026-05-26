package com.cloudpos.notification.dto;

import com.cloudpos.notification.notification.NotificationChannel;
import com.cloudpos.notification.notification.NotificationPriority;
import com.cloudpos.notification.notification.NotificationStatus;
import com.cloudpos.notification.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private String id;
    private String userId;
    private String tenantId;
    private String recipient;
    private String recipientType;
    private NotificationType notificationType;
    private NotificationChannel channel;
    private String title;
    private String message;
    private NotificationStatus status;
    private NotificationPriority priority;
    private String referenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
}
