package com.cloudpos.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OfferRequest {

	@NotBlank
	private String lowStockRequestId;

	@NotBlank
	private String shopTenantId;

	@NotBlank
	private String productName;

	@NotNull
	private Integer requestedQty;

	@NotNull
	private BigDecimal offeredPrice;

	@NotNull
	private Integer deliveryDays;

	private String note;
}
