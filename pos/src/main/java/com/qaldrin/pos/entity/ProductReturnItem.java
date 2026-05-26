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
public class ProductReturnItem {

	private String mysqlId;
	private String productId;
	private String productName;
	private BigDecimal returnedQuantity;
	private BigDecimal unitPrice;
	private BigDecimal returnAmount;
	private String saleItemId;
	private String tenantId;
	private String condition;
}
