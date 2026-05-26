package com.cloudpos.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequest {

	@NotBlank
	private String productName;

	private String brand;

	@NotBlank
	private String categoryName;

	@NotBlank
	private String unitType;

	@NotNull
	private Integer minimumOrderQty;

	@NotNull
	private Integer availableStock;

	@NotNull
	private BigDecimal price;

	private String description;
	private String imageUrl;
	private Boolean available;
}
