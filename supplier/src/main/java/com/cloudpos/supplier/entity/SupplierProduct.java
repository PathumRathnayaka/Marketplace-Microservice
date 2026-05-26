package com.cloudpos.supplier.entity;

import com.cloudpos.supplier.util.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "supplier_products")
public class SupplierProduct {

	@Id
	@Column(nullable = false, updatable = false, length = 36)
	private String id;

	@Column(nullable = false, length = 36)
	private String supplierId;

	@Column(nullable = false)
	private String productName;

	private String brand;
	private String categoryName;
	private String unitType;
	private Integer minimumOrderQty;
	private Integer availableStock;

	@Column(nullable = false, precision = 14, scale = 2)
	private BigDecimal price;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String imageUrl;

	@Column(name = "is_available", nullable = false)
	private boolean available;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	private boolean deleted;

	@PrePersist
	void prePersist() {
		if (id == null) {
			id = UuidGenerator.generate();
		}
	}
}
