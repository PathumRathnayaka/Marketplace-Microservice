package com.cloudpos.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {

    public static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private DateTimeUtil() {
    }

    public static LocalDateTime currentUtcTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime == null ? null : ISO_FORMATTER.format(dateTime);
    }
}
