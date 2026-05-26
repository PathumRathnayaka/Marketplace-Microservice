package com.cloudpos.common.util;

public final class CorrelationIdUtil {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    private CorrelationIdUtil() {
    }

    public static String generate() {
        return UuidUtil.generate();
    }

    public static String getOrGenerate(String correlationId) {
        return correlationId == null || correlationId.isBlank() ? generate() : correlationId;
    }
}
