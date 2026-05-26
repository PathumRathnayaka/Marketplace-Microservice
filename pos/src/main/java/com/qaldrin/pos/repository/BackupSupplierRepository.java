package com.qaldrin.pos.repository;

import com.qaldrin.pos.entity.BackupSupplier;
import java.util.Optional;

public interface BackupSupplierRepository extends TenantScopedRepository<BackupSupplier> {

	Optional<BackupSupplier> findByTenantIdAndMarketplaceSupplierId(String tenantId, String marketplaceSupplierId);
}
