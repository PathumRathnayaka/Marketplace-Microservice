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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "backup_grn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupGrn implements TenantScopedEntity, SoftDeletable, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String grnCode;
	private String supplierId;
	private String supplierName;
	private String invoiceNo;
	private LocalDate receivedDate;
	private BigDecimal totalAmount;
	private BigDecimal paidAmount;
	private BigDecimal dueAmount;
	private String status;
	private String paymentStatus;
	private LocalDateTime createdAt;
	private Boolean deleted;
	private String tenantId;
	private Long timestamp;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "backup_grn_items", joinColumns = @JoinColumn(name = "grn_id"))
	private List<GrnItem> items = new ArrayList<>();

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
