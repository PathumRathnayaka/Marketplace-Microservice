package com.cloudpos.notification.push;

import com.cloudpos.notification.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    @Override
    public void preparePushNotification(Notification notification) {
        log.info("Push notification prepared for future Firebase/mobile integration: {}", notification.getId());
    }
}
