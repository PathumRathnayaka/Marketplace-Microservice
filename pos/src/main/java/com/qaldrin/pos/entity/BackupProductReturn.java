package com.qaldrin.pos.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "backup_product_returns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupProductReturn implements TenantScopedEntity, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String returnCode;
	private String saleId;
	private String saleCode;
	private String customerId;
	private String customerContact;
	private String refundMethod;
	private BigDecimal refundAmount;
	private String reason;
	private LocalDateTime createdAt;
	private String tenantId;
	private Long timestamp;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "productreturnpostgres_returnitems", joinColumns = @JoinColumn(name = "productreturnpostgres_id"))
	private List<ProductReturnItem> returnItems = new ArrayList<>();

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
