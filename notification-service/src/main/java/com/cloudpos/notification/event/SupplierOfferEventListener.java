package com.cloudpos.notification.event;

import com.cloudpos.common.event.SupplierOfferEventDTO;
import com.cloudpos.notification.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumes supplier-offer events.
 * Routing key: supplier.offer.created
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SupplierOfferEventListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SUPPLIER_OFFER)
    public void onSupplierOfferEvent(SupplierOfferEventDTO event) {
        log.info("[SUPPLIER-OFFER EVENT] Received supplierId={} productId={}",
                event.getSupplierId(), event.getProductId());
        // TODO: notify relevant POS tenants about the new supplier offer
        // notificationService.sendSupplierOfferNotification(event);
    }
}
