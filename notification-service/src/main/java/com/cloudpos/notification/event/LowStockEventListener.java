package com.cloudpos.notification.event;

import com.cloudpos.common.event.LowStockRequestEventDTO;
import com.cloudpos.notification.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumes low-stock events published by pos-service.
 * Routing key: low.stock.created
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LowStockEventListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_LOW_STOCK)
    public void onLowStockEvent(LowStockRequestEventDTO event) {
        log.info("[LOW-STOCK EVENT] Received for tenant={} product='{}' requiredQty={}",
                event.getTenantId(), event.getProductName(), event.getRequiredQuantity());
        // TODO: send email/SMS to registered suppliers for this product category
        // notificationService.sendLowStockAlert(event);
    }
}
