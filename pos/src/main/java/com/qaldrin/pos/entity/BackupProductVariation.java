package com.qaldrin.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "backup_product_variations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupProductVariation implements TenantScopedEntity, SoftDeletable, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String productId;
	private String productName;
	private String variation;
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
