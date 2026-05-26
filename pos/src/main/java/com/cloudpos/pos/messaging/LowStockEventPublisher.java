package com.cloudpos.pos.messaging;

import com.cloudpos.common.event.LowStockRequestEventDTO;
import com.cloudpos.pos.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Publishes low-stock events to RabbitMQ when a product falls below its minimum
 * stock threshold.
 *
 * Routing key: low.stock.created
 * Exchange: cloudpos.events (Topic)
 *
 * Usage: inject this bean into your low-stock detection service/scheduled job
 * and call
 * {@link #publish(String, String, String, String, String, int)}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LowStockEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Publishes a low-stock event.
     *
     * @param tenantId         the tenant (shop) that has the low stock
     * @param shopName         human-readable shop name
     * @param productId        internal product ID
     * @param productName      human-readable product name
     * @param category         product category (used for supplier matching)
     * @param requiredQuantity quantity needed to replenish
     */
    public void publish(String tenantId, String shopName,
            String productId, String productName,
            String category, int requiredQuantity) {

        LowStockRequestEventDTO event = LowStockRequestEventDTO.builder()
                .requestId(UUID.randomUUID().toString())
                .tenantId(tenantId)
                .shopName(shopName)
                .productId(productId)
                .productName(productName)
                .category(category)
                .requiredQuantity(requiredQuantity)
                .createdAt(LocalDateTime.now())
                .build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_LOW_STOCK, event);
        log.info("[LOW-STOCK PUBLISHED] tenantId={} product='{}' qty={}", tenantId, productName, requiredQuantity);
    }
}
