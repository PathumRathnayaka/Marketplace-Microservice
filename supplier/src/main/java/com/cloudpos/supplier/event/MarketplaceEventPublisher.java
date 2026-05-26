package com.cloudpos.supplier.event;

public interface MarketplaceEventPublisher {

	void publishSupplierOfferCreated(SupplierOfferEventDTO event);

	void publishSupplierOfferCancelled(SupplierOfferEventDTO event);
}
