package com.qaldrin.pos.repository;

import com.qaldrin.pos.entity.BackupProduct;
import java.util.List;

public interface BackupProductRepository extends TenantScopedRepository<BackupProduct> {

	List<BackupProduct> findByMinimumQuantityIsNotNull();
}
