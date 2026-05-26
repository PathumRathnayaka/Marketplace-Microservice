package com.cloudpos.notification.push;

import com.cloudpos.notification.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationServiceImpl.class);

    @Override
    public void preparePushNotification(Notification notification) {
        log.info("Push notification prepared for future Firebase/mobile integration: {}", notification.getId());
    }
}
