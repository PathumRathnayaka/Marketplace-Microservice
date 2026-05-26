package com.cloudpos.notification.repository;

import com.cloudpos.notification.entity.SmsLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmsLogRepository extends JpaRepository<SmsLog, String> {

    List<SmsLog> findByNotificationId(String notificationId);
}
