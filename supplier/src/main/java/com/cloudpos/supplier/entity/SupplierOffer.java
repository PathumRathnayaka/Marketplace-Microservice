package com.cloudpos.supplier.entity;

import com.cloudpos.supplier.util.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier_offers")
public class SupplierOffer {

	@Id
	@Column(nullable = false, updatable = false, length = 36)
	private String id;

	@Column(nullable = false, length = 36)
	private String supplierId;

	@Column(nullable = false)
	private String lowStockRequestId;

	@Column(nullable = false)
	private String shopTenantId;

	@Column(nullable = false)
	private String productName;

	@Column(nullable = false)
	private Integer requestedQty;

	@Column(nullable = false, precision = 14, scale = 2)
	private BigDecimal offeredPrice;

	@Column(nullable = false)
	private Integer deliveryDays;

	@Column(columnDefinition = "TEXT")
	private String note;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OfferStatus status;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@PrePersist
	void prePersist() {
		if (id == null) {
			id = UuidGenerator.generate();
		}
		if (status == null) {
			status = OfferStatus.PENDING;
		}
	}
}
