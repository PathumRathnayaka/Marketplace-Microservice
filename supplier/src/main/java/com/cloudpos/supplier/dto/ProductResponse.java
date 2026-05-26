package com.cloudpos.supplier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProductResponse {

	private String id;
	private String supplierId;
	private String productName;
	private String brand;
	private String categoryName;
	private String unitType;
	private Integer minimumOrderQty;
	private Integer availableStock;
	private BigDecimal price;
	private String description;
	private String imageUrl;
	@JsonProperty("isAvailable")
	private boolean available;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
