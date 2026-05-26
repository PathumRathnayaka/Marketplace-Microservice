package com.cloudpos.notification.repository;

import com.cloudpos.notification.entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailLogRepository extends JpaRepository<EmailLog, String> {

    List<EmailLog> findByNotificationId(String notificationId);
}
