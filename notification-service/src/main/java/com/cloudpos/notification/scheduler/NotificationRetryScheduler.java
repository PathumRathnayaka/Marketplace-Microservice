package com.cloudpos.notification.scheduler;

import com.cloudpos.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationRetryScheduler {

    private static final Logger log = LoggerFactory.getLogger(NotificationRetryScheduler.class);

    private final NotificationService notificationService;

    public NotificationRetryScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedDelayString = "${notification.retry.fixed-delay-ms:300000}")
    public void retryFailedNotifications() {
        log.debug("Starting failed notification retry scan");
        notificationService.retryFailedNotifications();
    }
}
