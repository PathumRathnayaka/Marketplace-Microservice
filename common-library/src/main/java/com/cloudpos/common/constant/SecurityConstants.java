package com.cloudpos.common.constant;

public final class SecurityConstants {

    public static final String JWT_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION = 86_400_000L;
    public static final long REFRESH_TOKEN_EXPIRATION = 604_800_000L;

    private SecurityConstants() {
    }
}
