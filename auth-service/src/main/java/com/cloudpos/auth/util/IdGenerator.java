package com.cloudpos.auth.util;

import java.util.UUID;

public final class IdGenerator {

    private IdGenerator() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String generateTenantCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder("TENANT_");
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
