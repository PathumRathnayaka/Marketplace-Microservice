package com.qaldrin.pos.integration.marketplace;

import com.qaldrin.pos.entity.LowStockRequest;
import org.springframework.stereotype.Component;

@Component
public class MarketplaceEventPublisher {

	public void publishLowStockCreatedEvent(LowStockRequest request) {
		MarketplaceLowStockDTO event = new MarketplaceLowStockDTO(
				request.getId(),
				request.getTenantId(),
				request.getProductId(),
				request.getProductName(),
				request.getCategory(),
				request.getCurrentQuantity(),
				request.getMinimumQuantity(),
				request.getRequiredQuantity()
		);
		publishLowStockCreatedEvent(event);
	}

	public void publishLowStockCreatedEvent(MarketplaceLowStockDTO event) {
		// Future integration point: publish to RabbitMQ or another event broker.
	}
}
