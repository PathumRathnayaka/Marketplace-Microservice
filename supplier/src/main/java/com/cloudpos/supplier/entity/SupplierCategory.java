package com.cloudpos.supplier.entity;

import com.cloudpos.supplier.util.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier_categories")
public class SupplierCategory {

	@Id
	@Column(nullable = false, updatable = false, length = 36)
	private String id;

	@Column(nullable = false, length = 36)
	private String supplierId;

	@Column(nullable = false)
	private String categoryName;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	void prePersist() {
		if (id == null) {
			id = UuidGenerator.generate();
		}
	}
}
