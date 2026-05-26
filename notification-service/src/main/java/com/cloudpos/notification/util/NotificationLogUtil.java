package com.cloudpos.notification.util;

import org.slf4j.Logger;

public final class NotificationLogUtil {

    private NotificationLogUtil() {
    }

    public static void deliveryAttempt(Logger log, String notificationId, String channel, String recipient) {
        log.info("notificationId={} channel={} recipient={} event=delivery_attempt", notificationId, channel, recipient);
    }
}
