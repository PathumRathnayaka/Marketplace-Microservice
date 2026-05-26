package com.cloudpos.notification.dto;

import com.cloudpos.notification.notification.NotificationChannel;
import com.cloudpos.notification.notification.NotificationPriority;
import com.cloudpos.notification.notification.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    public SendNotificationRequestDTO() {
    }

    public SendNotificationRequestDTO(String userId, String tenantId, String recipient, String recipientType,
            NotificationType notificationType, NotificationChannel channel, String title,
            String message, NotificationPriority priority, String referenceId) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.recipient = recipient;
        this.recipientType = recipientType;
        this.notificationType = notificationType;
        this.channel = channel;
        this.title = title;
        this.message = message;
        this.priority = priority;
        this.referenceId = referenceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationPriority getPriority() {
        return priority;
    }

    public void setPriority(NotificationPriority priority) {
        this.priority = priority;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
