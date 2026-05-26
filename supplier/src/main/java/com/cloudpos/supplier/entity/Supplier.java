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
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {

	@Id
	@Column(nullable = false, updatable = false, length = 36)
	private String id;

	@Column(nullable = false)
	private String businessName;

	@Column(nullable = false)
	private String ownerName;

	@Column(nullable = false, unique = true)
	private String email;

	private String phone;

	@Column(nullable = false)
	private String password;

	private String businessType;
	private String district;
	private String city;
	private String address;
	private String profileImage;

	@Column(name = "is_verified", nullable = false)
	private boolean verified;

	@Column(name = "is_active", nullable = false)
	private boolean active;

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
