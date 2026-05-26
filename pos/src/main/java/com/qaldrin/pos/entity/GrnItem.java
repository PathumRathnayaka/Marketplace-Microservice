package com.qaldrin.pos.entity;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrnItem {

	private String mysqlId;
	private String productId;
	private String productName;
	private String variationId;
	private String variationSize;
	private BigDecimal quantity;
	private BigDecimal purchasePrice;
	private BigDecimal salePrice;
	private LocalDate expireDate;
	private String barcode;
	private String batchCode;
	private String brand;
	private BigDecimal ourPrice;
	private BigDecimal tax;
	private BigDecimal discount;
	private String warehouseNo;
	private String tenantId;
	private LocalDateTime createdAt;
}
