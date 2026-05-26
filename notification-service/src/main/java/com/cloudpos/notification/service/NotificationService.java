package com.cloudpos.notification.service;

import com.cloudpos.notification.dto.NotificationResponseDTO;
import com.cloudpos.notification.dto.SendNotificationRequestDTO;
import com.cloudpos.notification.notification.NotificationStatus;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO sendNotification(SendNotificationRequestDTO request);

    List<NotificationResponseDTO> getNotifications();

    NotificationResponseDTO getNotificationById(String id);

    NotificationResponseDTO markAsRead(String id);

    void softDelete(String id);

    void updateStatus(String id, NotificationStatus status);

    void retryFailedNotifications();
}
