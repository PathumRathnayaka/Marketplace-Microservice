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
public class SmsResponseDTO {

    private String id;
    private String notificationId;
    private String phoneNumber;
    private NotificationStatus status;
    private String providerResponse;
    private String errorMessage;
    private int retryCount;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}
