package com.cloudpos.notification.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityPreparationConfig {

    public static final String AUTHENTICATED_USER_HEADER = "X-Authenticated-User";
    public static final String TENANT_ID_HEADER = "X-Tenant-Id";
}
