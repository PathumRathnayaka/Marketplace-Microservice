package com.qaldrin.pos.repository;

import com.qaldrin.pos.entity.BackupProductQuantityBatch;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BackupProductQuantityBatchRepository extends TenantScopedRepository<BackupProductQuantityBatch> {

	@Query("""
			select coalesce(sum(batch.quantity), 0)
			from BackupProductQuantityBatch batch
			where batch.tenantId = :tenantId
				and batch.productId = :productId
				and (batch.deleted is null or batch.deleted = false)
			""")
	BigDecimal sumQuantityByTenantAndProduct(@Param("tenantId") String tenantId, @Param("productId") String productId);
}
