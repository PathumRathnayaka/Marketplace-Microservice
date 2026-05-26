package com.cloudpos.notification.entity;

import com.cloudpos.notification.notification.NotificationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
