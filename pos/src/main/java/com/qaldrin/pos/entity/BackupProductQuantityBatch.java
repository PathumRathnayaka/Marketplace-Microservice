package com.qaldrin.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "backup_product_quantity_batches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupProductQuantityBatch implements TenantScopedEntity, SoftDeletable, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String productId;
	private String productName;
	private String variationId;
	private String variationSize;
	private String supplierId;
	private String supplierName;
	private String barcode;
	private BigDecimal quantity;
	private String batchCode;
	private BigDecimal salePrice;
	private BigDecimal purchasePrice;
	private BigDecimal ourPrice;
	private String brand;
	private String warehouseNo;
	private BigDecimal discount;
	private BigDecimal tax;
	private LocalDate expireDate;
	private LocalDateTime createdAt;
	private Boolean deleted;
	private String tenantId;
	private Long timestamp;

	@PrePersist
	void prePersist() {
		if (id == null || id.isBlank()) {
			id = UUID.randomUUID().toString();
		}
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
		if (deleted == null) {
			deleted = false;
		}
		if (timestamp == null) {
			timestamp = System.currentTimeMillis();
		}
	}
}
