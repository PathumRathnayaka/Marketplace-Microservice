package com.cloudpos.notification.event;

import com.cloudpos.common.event.SupplierOfferEventDTO;
import com.cloudpos.notification.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumes supplier-offer events.
 * Routing key: supplier.offer.created
 */
@Component
public class SupplierOfferEventListener {

    private static final Logger log = LoggerFactory.getLogger(SupplierOfferEventListener.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SUPPLIER_OFFER)
    public void onSupplierOfferEvent(SupplierOfferEventDTO event) {
        log.info("[SUPPLIER-OFFER EVENT] Received supplierId={} offerPrice={}",
                event.getSupplierId(), event.getOfferedPrice());
        // TODO: notify relevant POS tenants about the new supplier offer
        // notificationService.sendSupplierOfferNotification(event);
    }
}
