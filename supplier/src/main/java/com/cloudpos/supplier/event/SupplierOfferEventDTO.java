package com.cloudpos.supplier.event;

import com.cloudpos.supplier.entity.OfferStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOfferEventDTO {

	private String offerId;
	private String supplierId;
	private String lowStockRequestId;
	private String shopTenantId;
	private String productName;
	private Integer requestedQty;
	private BigDecimal offeredPrice;
	private Integer deliveryDays;
	private OfferStatus status;
	private LocalDateTime occurredAt;
}
