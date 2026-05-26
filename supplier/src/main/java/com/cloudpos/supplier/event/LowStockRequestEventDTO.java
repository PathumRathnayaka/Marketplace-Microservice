package com.cloudpos.supplier.event;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LowStockRequestEventDTO {

	private String lowStockRequestId;
	private String shopTenantId;
	private String productName;
	private Integer requestedQty;
	private String unitType;
	private String district;
	private String city;
	private LocalDateTime requestedAt;
}
