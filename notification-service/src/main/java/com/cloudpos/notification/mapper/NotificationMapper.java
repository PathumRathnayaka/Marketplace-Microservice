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
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setTenantId(request.getTenantId());
        notification.setRecipient(request.getRecipient());
        notification.setRecipientType(request.getRecipientType());
        notification.setNotificationType(request.getNotificationType());
        notification.setChannel(request.getChannel());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setPriority(request.getPriority() == null ? NotificationPriority.NORMAL : request.getPriority());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setReferenceId(request.getReferenceId());
        notification.setDeleted(false);
        return notification;
    }

    public NotificationResponseDTO toResponse(Notification notification) {
        NotificationResponseDTO response = new NotificationResponseDTO();
        response.setId(notification.getId());
        response.setUserId(notification.getUserId());
        response.setTenantId(notification.getTenantId());
        response.setRecipient(notification.getRecipient());
        response.setRecipientType(notification.getRecipientType());
        response.setNotificationType(notification.getNotificationType());
        response.setChannel(notification.getChannel());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setStatus(notification.getStatus());
        response.setPriority(notification.getPriority());
        response.setReferenceId(notification.getReferenceId());
        response.setCreatedAt(notification.getCreatedAt());
        response.setUpdatedAt(notification.getUpdatedAt());
        response.setSentAt(notification.getSentAt());
        response.setReadAt(notification.getReadAt());
        return response;
    }

    public EmailResponseDTO toEmailResponse(EmailLog emailLog) {
        EmailResponseDTO response = new EmailResponseDTO();
        response.setId(emailLog.getId());
        response.setNotificationId(emailLog.getNotificationId());
        response.setRecipientEmail(emailLog.getRecipientEmail());
        response.setSubject(emailLog.getSubject());
        response.setStatus(emailLog.getStatus());
        response.setErrorMessage(emailLog.getErrorMessage());
        response.setRetryCount(emailLog.getRetryCount());
        response.setProviderResponse(emailLog.getProviderResponse());
        response.setCreatedAt(emailLog.getCreatedAt());
        response.setSentAt(emailLog.getSentAt());
        return response;
    }

    public SmsResponseDTO toSmsResponse(SmsLog smsLog) {
        SmsResponseDTO response = new SmsResponseDTO();
        response.setId(smsLog.getId());
        response.setNotificationId(smsLog.getNotificationId());
        response.setPhoneNumber(smsLog.getPhoneNumber());
        response.setStatus(smsLog.getStatus());
        response.setProviderResponse(smsLog.getProviderResponse());
        response.setErrorMessage(smsLog.getErrorMessage());
        response.setRetryCount(smsLog.getRetryCount());
        response.setCreatedAt(smsLog.getCreatedAt());
        response.setSentAt(smsLog.getSentAt());
        return response;
    }
}
