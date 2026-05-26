package com.cloudpos.notification.mapper;

import com.cloudpos.notification.dto.EmailResponseDTO;
import com.cloudpos.notification.dto.NotificationResponseDTO;
import com.cloudpos.notification.dto.SendNotificationRequestDTO;
import com.cloudpos.notification.dto.SmsResponseDTO;
import com.cloudpos.notification.entity.EmailLog;
import com.cloudpos.notification.entity.Notification;
import com.cloudpos.notification.entity.SmsLog;
import com.cloudpos.notification.notification.NotificationPriority;
import com.cloudpos.notification.notification.NotificationStatus;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toEntity(SendNotificationRequestDTO request) {
        return Notification.builder()
                .userId(request.getUserId())
                .tenantId(request.getTenantId())
                .recipient(request.getRecipient())
                .recipientType(request.getRecipientType())
                .notificationType(request.getNotificationType())
                .channel(request.getChannel())
                .title(request.getTitle())
                .message(request.getMessage())
                .priority(request.getPriority() == null ? NotificationPriority.NORMAL : request.getPriority())
                .status(NotificationStatus.PENDING)
                .referenceId(request.getReferenceId())
                .deleted(false)
                .build();
    }

    public NotificationResponseDTO toResponse(Notification notification) {
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .tenantId(notification.getTenantId())
                .recipient(notification.getRecipient())
                .recipientType(notification.getRecipientType())
                .notificationType(notification.getNotificationType())
                .channel(notification.getChannel())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .priority(notification.getPriority())
                .referenceId(notification.getReferenceId())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .sentAt(notification.getSentAt())
                .readAt(notification.getReadAt())
                .build();
    }

    public EmailResponseDTO toEmailResponse(EmailLog emailLog) {
        return EmailResponseDTO.builder()
                .id(emailLog.getId())
                .notificationId(emailLog.getNotificationId())
                .recipientEmail(emailLog.getRecipientEmail())
                .subject(emailLog.getSubject())
                .status(emailLog.getStatus())
                .errorMessage(emailLog.getErrorMessage())
                .retryCount(emailLog.getRetryCount())
                .providerResponse(emailLog.getProviderResponse())
                .createdAt(emailLog.getCreatedAt())
                .sentAt(emailLog.getSentAt())
                .build();
    }

    public SmsResponseDTO toSmsResponse(SmsLog smsLog) {
        return SmsResponseDTO.builder()
                .id(smsLog.getId())
                .notificationId(smsLog.getNotificationId())
                .phoneNumber(smsLog.getPhoneNumber())
                .status(smsLog.getStatus())
                .providerResponse(smsLog.getProviderResponse())
                .errorMessage(smsLog.getErrorMessage())
                .retryCount(smsLog.getRetryCount())
                .createdAt(smsLog.getCreatedAt())
                .sentAt(smsLog.getSentAt())
                .build();
    }
}
