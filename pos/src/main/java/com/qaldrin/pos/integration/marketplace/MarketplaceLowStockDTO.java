package com.qaldrin.pos.integration.marketplace;

import java.math.BigDecimal;

public record MarketplaceLowStockDTO(
		String requestId,
		String tenantId,
		String productId,
		String productName,
		String category,
		BigDecimal currentQuantity,
		BigDecimal minimumQuantity,
		BigDecimal requiredQuantity
) {
}
