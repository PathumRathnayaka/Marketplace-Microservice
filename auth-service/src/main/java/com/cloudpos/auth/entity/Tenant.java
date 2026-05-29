package com.cloudpos.auth.entity;

import com.cloudpos.auth.util.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @Column(nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "tenant_code", nullable = false, unique = true, length = 20)
    private String tenantCode;

    @Column(name = "business_name", nullable = false, length = 150)
    private String businessName;

    @Column(name = "owner_email", nullable = false, length = 150)
    private String ownerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TenantStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        if (id == null) {
            id = IdGenerator.uuid();
        }
        if (tenantCode == null) {
            tenantCode = IdGenerator.generateTenantCode();
        }
        if (status == null) {
            status = TenantStatus.PENDING;
        }
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
