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
@Table(name = "backup_grn_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupGrnPayment implements TenantScopedEntity, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String grnMysqlId;
	private LocalDateTime paymentDate;
	private BigDecimal amount;
	private String paymentMethod;
	private String referenceNo;
	private String notes;
	private LocalDateTime createdAt;
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
		if (timestamp == null) {
			timestamp = System.currentTimeMillis();
		}
	}
}
