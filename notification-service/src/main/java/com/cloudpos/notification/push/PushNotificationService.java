package com.cloudpos.notification.push;

import com.cloudpos.notification.entity.Notification;

public interface PushNotificationService {

    void preparePushNotification(Notification notification);
}
