package com.cloudpos.notification.dto;

import com.cloudpos.notification.notification.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponseDTO {

    private String id;
    private String notificationId;
    private String recipientEmail;
    private String subject;
    private NotificationStatus status;
    private String errorMessage;
    private int retryCount;
    private String providerResponse;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}
