package com.cloudpos.supplier.mapper;

import com.cloudpos.supplier.dto.OfferResponse;
import com.cloudpos.supplier.entity.SupplierOffer;
import org.springframework.stereotype.Component;

@Component
public class OfferMapper {

	public OfferResponse toResponse(SupplierOffer offer) {
		return OfferResponse.builder()
				.id(offer.getId())
				.supplierId(offer.getSupplierId())
				.lowStockRequestId(offer.getLowStockRequestId())
				.shopTenantId(offer.getShopTenantId())
				.productName(offer.getProductName())
				.requestedQty(offer.getRequestedQty())
				.offeredPrice(offer.getOfferedPrice())
				.deliveryDays(offer.getDeliveryDays())
				.note(offer.getNote())
				.status(offer.getStatus())
				.createdAt(offer.getCreatedAt())
				.updatedAt(offer.getUpdatedAt())
				.build();
	}
}
