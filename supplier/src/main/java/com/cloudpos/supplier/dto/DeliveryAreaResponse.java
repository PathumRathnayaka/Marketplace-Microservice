package com.cloudpos.supplier.dto;

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
public class DeliveryAreaResponse {

	private String id;
	private String supplierId;
	private String district;
	private String city;
	private BigDecimal deliveryFee;
	private Integer estimatedDays;
	private LocalDateTime createdAt;
}
