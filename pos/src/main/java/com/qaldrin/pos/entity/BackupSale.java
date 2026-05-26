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
@Table(name = "backup_sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupSale implements TenantScopedEntity, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String saleId;
	private String customerId;
	private String customerContact;
	private String cashierId;
	private String cashierName;
	private BigDecimal subTotal;
	private BigDecimal taxAmount;
	private BigDecimal discountAmount;
	private BigDecimal totalAmount;
	private BigDecimal paidAmount;
	private BigDecimal changeAmount;
	private String paymentMethod;
	private LocalDateTime saleDate;
	private String tenantId;
	private Long timestamp;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "salepostgres_saleitems", joinColumns = @JoinColumn(name = "salepostgres_id"))
	private List<SaleItem> saleItems = new ArrayList<>();

	@PrePersist
	void prePersist() {
		if (id == null || id.isBlank()) {
			id = UUID.randomUUID().toString();
		}
		if (saleDate == null) {
			saleDate = LocalDateTime.now();
		}
		if (timestamp == null) {
			timestamp = System.currentTimeMillis();
		}
	}
}
