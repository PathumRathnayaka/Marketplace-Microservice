package com.qaldrin.pos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "supplier_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCategory implements TenantScopedEntity, SoftDeletable {

	@Id
	private String id;

	@Column(name = "tenant_id", nullable = false)
	private String tenantId;

	@Column(name = "supplier_id", nullable = false)
	private String supplierId;

	@Column(name = "category_name", nullable = false)
	private String categoryName;

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
