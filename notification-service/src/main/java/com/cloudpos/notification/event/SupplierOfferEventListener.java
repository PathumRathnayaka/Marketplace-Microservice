package com.cloudpos.notification.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SupplierOfferEventListener {

    public void onSupplierOfferEvent(Object event) {
        log.info("Supplier offer event listener prepared for RabbitMQ integration");
    }
}
