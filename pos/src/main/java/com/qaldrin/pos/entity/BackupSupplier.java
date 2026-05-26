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
@Table(name = "backup_suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupSupplier implements TenantScopedEntity, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String name;
	private String contact;
	private String representativeName;
	private String phone;
	private String email;
	private String address;
	private String supplierSource;
	private String marketplaceSupplierId;
	private Boolean marketplaceConnected;
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
		if (supplierSource == null || supplierSource.isBlank()) {
			supplierSource = "LOCAL";
		}
		if (marketplaceConnected == null) {
			marketplaceConnected = false;
		}
		if (timestamp == null) {
			timestamp = System.currentTimeMillis();
		}
	}
}
