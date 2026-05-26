package com.qaldrin.pos.repository;

import com.qaldrin.pos.entity.LowStockRequest;
import com.qaldrin.pos.entity.LowStockStatus;
import java.util.List;
import java.util.Optional;

public interface LowStockRequestRepository extends TenantScopedRepository<LowStockRequest> {

	List<LowStockRequest> findByTenantIdAndStatusAndDeletedFalse(String tenantId, LowStockStatus status);

	Optional<LowStockRequest> findFirstByTenantIdAndProductIdAndStatusAndDeletedFalse(String tenantId, String productId, LowStockStatus status);

	boolean existsByTenantIdAndProductIdAndStatusAndDeletedFalse(String tenantId, String productId, LowStockStatus status);
}
