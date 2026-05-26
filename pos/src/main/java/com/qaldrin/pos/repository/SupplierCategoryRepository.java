package com.qaldrin.pos.repository;

import com.qaldrin.pos.entity.SupplierCategory;
import java.util.List;

public interface SupplierCategoryRepository extends TenantScopedRepository<SupplierCategory> {

	List<SupplierCategory> findByTenantIdAndSupplierIdAndDeletedFalse(String tenantId, String supplierId);
}
