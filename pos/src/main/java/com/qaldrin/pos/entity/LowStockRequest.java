package com.qaldrin.pos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "low_stock_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LowStockRequest implements TenantScopedEntity, SoftDeletable {

	@Id
	private String id;

	@Column(name = "tenant_id", nullable = false)
	private String tenantId;

	@Column(name = "product_id", nullable = false)
	private String productId;

	@Column(name = "product_name")
	private String productName;

	private String category;

	@Column(name = "current_quantity")
	private BigDecimal currentQuantity;

	@Column(name = "minimum_quantity")
	private BigDecimal minimumQuantity;

	@Column(name = "required_quantity")
	private BigDecimal requiredQuantity;

	@Enumerated(EnumType.STRING)
	private LowStockStatus status;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	private Boolean deleted;

	@PrePersist
	void prePersist() {
		if (id == null || id.isBlank()) {
			id = UUID.randomUUID().toString();
		}
		if (status == null) {
			status = LowStockStatus.OPEN;
		}
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
		if (updatedAt == null) {
			updatedAt = createdAt;
		}
		if (deleted == null) {
			deleted = false;
		}
	}

	@PreUpdate
	void preUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
