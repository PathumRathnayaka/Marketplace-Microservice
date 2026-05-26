package com.cloudpos.notification.entity;

import com.cloudpos.notification.notification.NotificationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_logs")
public class EmailLog {

    @Id
    private String id;
    private String notificationId;
    private String recipientEmail;
    private String subject;

    @Column(length = 8000)
    private String body;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(length = 2000)
    private String errorMessage;

    private int retryCount;

    @Column(length = 4000)
    private String providerResponse;

    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    public EmailLog() {
    }

    @PrePersist
    void prePersist() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        if (status == null) {
            status = NotificationStatus.PENDING;
        }
        createdAt = LocalDateTime.now();
    }

    // --- GETTERS & SETTERS ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getProviderResponse() {
        return providerResponse;
    }

    public void setProviderResponse(String providerResponse) {
        this.providerResponse = providerResponse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    // --- MANUAL BUILDER PATTERN ---

    public static EmailLogBuilder builder() {
        return new EmailLogBuilder();
    }

    public static class EmailLogBuilder {
        private String id;
        private String notificationId;
        private String recipientEmail;
        private String subject;
        private String body;
        private NotificationStatus status;
        private String errorMessage;
        private int retryCount;
        private String providerResponse;
        private LocalDateTime createdAt;
        private LocalDateTime sentAt;

        public EmailLogBuilder id(String id) {
            this.id = id;
            return this;
        }

        public EmailLogBuilder notificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public EmailLogBuilder recipientEmail(String recipientEmail) {
            this.recipientEmail = recipientEmail;
            return this;
        }

        public EmailLogBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailLogBuilder body(String body) {
            this.body = body;
            return this;
        }

        public EmailLogBuilder status(NotificationStatus status) {
            this.status = status;
            return this;
        }

        public EmailLogBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public EmailLogBuilder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public EmailLogBuilder providerResponse(String providerResponse) {
            this.providerResponse = providerResponse;
            return this;
        }

        public EmailLogBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EmailLogBuilder sentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
            return this;
        }

        public EmailLog build() {
            EmailLog emailLog = new EmailLog();
            emailLog.setId(this.id);
            emailLog.setNotificationId(this.notificationId);
            emailLog.setRecipientEmail(this.recipientEmail);
            emailLog.setSubject(this.subject);
            emailLog.setBody(this.body);
            emailLog.setStatus(this.status);
            emailLog.setErrorMessage(this.errorMessage);
            emailLog.setRetryCount(this.retryCount);
            emailLog.setProviderResponse(this.providerResponse);
            emailLog.setCreatedAt(this.createdAt);
            emailLog.setSentAt(this.sentAt);
            return emailLog;
        }
    }
}
