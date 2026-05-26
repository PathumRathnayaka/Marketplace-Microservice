package com.cloudpos.notification.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String LOW_STOCK_QUEUE = "notification.low-stock";
    public static final String SUPPLIER_OFFER_QUEUE = "notification.supplier-offer";
    public static final String USER_REGISTERED_QUEUE = "notification.user-registered";
}
