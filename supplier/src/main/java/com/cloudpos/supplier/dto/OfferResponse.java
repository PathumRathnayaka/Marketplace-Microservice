package com.cloudpos.supplier.dto;

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
public class OfferResponse {

	private String id;
	private String supplierId;
	private String lowStockRequestId;
	private String shopTenantId;
	private String productName;
	private Integer requestedQty;
	private BigDecimal offeredPrice;
	private Integer deliveryDays;
	private String note;
	private OfferStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
