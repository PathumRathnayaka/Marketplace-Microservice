package com.cloudpos.notification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI notificationOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cloud POS Notification Service API")
                        .version("1.0.0")
                        .description("Notification infrastructure APIs for Cloud POS SaaS"));
    }
}
