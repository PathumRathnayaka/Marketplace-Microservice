package com.qaldrin.pos.repository;

import com.qaldrin.pos.entity.SupplierOffer;
import java.util.List;

public interface SupplierOfferRepository extends TenantScopedRepository<SupplierOffer> {

	List<SupplierOffer> findByTenantIdAndRequestIdAndDeletedFalse(String tenantId, String requestId);
}
