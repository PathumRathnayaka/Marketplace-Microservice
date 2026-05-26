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
@Table(name = "backup_wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupWallet implements TenantScopedEntity, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String customerId;
	private String customerContact;
	private BigDecimal balance;
	private LocalDateTime lastUpdated;
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
		if (lastUpdated == null) {
			lastUpdated = LocalDateTime.now();
		}
		if (timestamp == null) {
			timestamp = System.currentTimeMillis();
		}
	}
}
