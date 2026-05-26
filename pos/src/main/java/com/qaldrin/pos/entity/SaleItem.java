package com.qaldrin.pos.entity;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {

	private String mysqlId;
	private String category;
	private String productId;
	private String productName;
	private BigDecimal quantity;
	private BigDecimal returnedQuantity;
	private BigDecimal unitPrice;
	private BigDecimal subtotal;
	private String tenantId;
	private BigDecimal ourPrice;
}
