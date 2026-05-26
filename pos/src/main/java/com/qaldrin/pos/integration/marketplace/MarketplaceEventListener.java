package com.qaldrin.pos.integration.marketplace;

import org.springframework.stereotype.Component;

@Component
public class MarketplaceEventListener {

	public void onMarketplaceSupplierLinked(MarketplaceSupplierDTO supplier) {
		// Future integration point: consume supplier marketplace events.
	}
}
