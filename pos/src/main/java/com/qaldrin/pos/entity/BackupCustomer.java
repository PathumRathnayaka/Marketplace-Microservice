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
@Table(name = "backup_customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackupCustomer implements TenantScopedEntity, HasTimestamp {

	@Id
	private String id;
	private String mysqlId;
	private String contact;
	private String email;
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
		if (timestamp == null) {
			timestamp = System.currentTimeMillis();
		}
	}
}
