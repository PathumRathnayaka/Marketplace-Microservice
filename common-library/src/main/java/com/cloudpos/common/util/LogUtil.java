package com.cloudpos.common.util;

import org.slf4j.Logger;

public final class LogUtil {

    private LogUtil() {
    }

    public static void structured(Logger log, String correlationId, String event, String message) {
        log.info("correlationId={} event={} message={}", correlationId, event, message);
    }

    public static void request(Logger log, String correlationId, String method, String path) {
        log.info("correlationId={} requestMethod={} requestPath={}", correlationId, method, path);
    }

    public static void service(Logger log, String serviceName, String message) {
        log.info("service={} message={}", serviceName, message);
    }
}
