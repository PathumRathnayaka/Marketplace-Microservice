package com.cloudpos.notification.event;

import com.cloudpos.common.event.LowStockRequestEventDTO;
import com.cloudpos.notification.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumes low-stock events published by pos-service.
 * Routing key: low.stock.created
 */
@Component
public class LowStockEventListener {

    private static final Logger log = LoggerFactory.getLogger(LowStockEventListener.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_LOW_STOCK)
    public void onLowStockEvent(LowStockRequestEventDTO event) {
        log.info("[LOW-STOCK EVENT] Received for tenant={} product='{}' requiredQty={}",
                event.getTenantId(), event.getProductName(), event.getRequiredQuantity());
        // TODO: send email/SMS to registered suppliers for this product category
        // notificationService.sendLowStockAlert(event);
    }
}
