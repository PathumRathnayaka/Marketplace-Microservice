package com.cloudpos.notification.util;

import java.util.UUID;

public final class NotificationIdGenerator {

    private NotificationIdGenerator() {
    }

    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
