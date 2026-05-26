package com.cloudpos.notification.scheduler;

import com.cloudpos.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRetryScheduler {

    private final NotificationService notificationService;

    @Scheduled(fixedDelayString = "${notification.retry.fixed-delay-ms:300000}")
    public void retryFailedNotifications() {
        log.debug("Starting failed notification retry scan");
        notificationService.retryFailedNotifications();
    }
}
