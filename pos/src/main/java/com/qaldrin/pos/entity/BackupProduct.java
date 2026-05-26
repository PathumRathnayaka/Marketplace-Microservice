package com.qaldrin.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "backup_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupProduct implements TenantScopedEntity, SoftDeletable, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String name;
	private String category;
	private String unitType;
	private String status;
	private BigDecimal minimumQuantity;
	private Boolean deleted;
	private LocalDateTime createdDate;
	private String tenantId;
	private Long timestamp;

	@PrePersist
	void prePersist() {
		if (id == null || id.isBlank()) {
			id = UUID.randomUUID().toString();
		}
		if (createdDate == null) {
			createdDate = LocalDateTime.now();
		}
		if (deleted == null) {
			deleted = false;
		}
		if (timestamp == null) {
			timestamp = System.currentTimeMillis();
		}
	}
}
