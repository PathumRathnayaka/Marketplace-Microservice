package com.cloudpos.notification.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LowStockEventListener {

    public void onLowStockEvent(Object event) {
        log.info("Low stock event listener prepared for RabbitMQ integration");
    }
}
