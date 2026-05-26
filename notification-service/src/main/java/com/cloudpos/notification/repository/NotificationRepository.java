package com.cloudpos.notification.repository;

import com.cloudpos.notification.entity.Notification;
import com.cloudpos.notification.notification.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findByUserId(String userId);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByRecipient(String recipient);

    List<Notification> findByUserIdAndDeletedFalse(String userId);

    List<Notification> findByStatusAndDeletedFalse(NotificationStatus status);

    List<Notification> findByRecipientAndDeletedFalse(String recipient);
}
