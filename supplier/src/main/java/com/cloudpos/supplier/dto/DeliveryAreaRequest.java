package com.cloudpos.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class DeliveryAreaRequest {

	@NotBlank
	private String district;

	@NotBlank
	private String city;

	@NotNull
	private BigDecimal deliveryFee;

	@NotNull
	private Integer estimatedDays;
}
