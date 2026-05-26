package com.qaldrin.pos.service;

import com.qaldrin.pos.entity.BackupGrn;
import com.qaldrin.pos.entity.BackupSupplier;
import com.qaldrin.pos.entity.BackupProductReturn;
import com.qaldrin.pos.entity.BackupSale;
import com.qaldrin.pos.entity.HasTimestamp;
import com.qaldrin.pos.entity.SoftDeletable;
import com.qaldrin.pos.entity.TenantScopedEntity;
import com.qaldrin.pos.exception.ResourceNotFoundException;
import com.qaldrin.pos.repository.TenantScopedRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantCrudService {

	@Transactional
	public <T extends TenantScopedEntity> T create(String resourceName, String tenantId, T entity, TenantScopedRepository<T> repository) {
		if (entity.getId() == null || entity.getId().isBlank()) {
			entity.setId(UUID.randomUUID().toString());
		}
		prepareForTenant(entity, tenantId);
		return repository.save(entity);
	}

	@Transactional(readOnly = true)
	public <T extends TenantScopedEntity> List<T> list(String tenantId, TenantScopedRepository<T> repository) {
		return repository.findByTenantId(tenantId).stream()
				.filter(this::isVisible)
				.toList();
	}

	@Transactional(readOnly = true)
	public <T extends TenantScopedEntity> T get(String resourceName, String tenantId, String id, TenantScopedRepository<T> repository) {
		T entity = repository.findByIdAndTenantId(id, tenantId)
				.orElseThrow(() -> new ResourceNotFoundException(resourceName + " not found"));
		if (!isVisible(entity)) {
			throw new ResourceNotFoundException(resourceName + " not found");
		}
		return entity;
	}

	@Transactional
	public <T extends TenantScopedEntity> T update(String resourceName, String tenantId, String id, T entity, TenantScopedRepository<T> repository) {
		get(resourceName, tenantId, id, repository);
		entity.setId(id);
		prepareForTenant(entity, tenantId);
		return repository.save(entity);
	}

	@Transactional
	public <T extends TenantScopedEntity> void delete(String resourceName, String tenantId, String id, TenantScopedRepository<T> repository) {
		T entity = get(resourceName, tenantId, id, repository);
		if (entity instanceof SoftDeletable softDeletable) {
			softDeletable.setDeleted(true);
			touch(entity);
			repository.save(entity);
			return;
		}
		repository.delete(entity);
	}

	private <T extends TenantScopedEntity> void prepareForTenant(T entity, String tenantId) {
		if (tenantId == null || tenantId.isBlank()) {
			throw new IllegalArgumentException("X-Tenant-Id header is required");
		}
		entity.setTenantId(tenantId);
		if (entity instanceof SoftDeletable softDeletable && softDeletable.getDeleted() == null) {
			softDeletable.setDeleted(false);
		}
		if (entity instanceof BackupSupplier supplier) {
			prepareSupplier(supplier);
		}
		if (entity instanceof BackupGrn grn && grn.getItems() != null) {
			grn.getItems().forEach(item -> item.setTenantId(tenantId));
		}
		if (entity instanceof BackupSale sale && sale.getSaleItems() != null) {
			sale.getSaleItems().forEach(item -> item.setTenantId(tenantId));
		}
		if (entity instanceof BackupProductReturn productReturn && productReturn.getReturnItems() != null) {
			productReturn.getReturnItems().forEach(item -> item.setTenantId(tenantId));
		}
		touch(entity);
	}

	private void prepareSupplier(BackupSupplier supplier) {
		if (supplier.getSupplierSource() == null || supplier.getSupplierSource().isBlank()) {
			supplier.setSupplierSource("LOCAL");
		}
		String supplierSource = supplier.getSupplierSource().trim().toUpperCase();
		if (!supplierSource.equals("LOCAL") && !supplierSource.equals("MARKETPLACE")) {
			throw new IllegalArgumentException("supplierSource must be LOCAL or MARKETPLACE");
		}
		supplier.setSupplierSource(supplierSource);
		if (supplierSource.equals("LOCAL")) {
			supplier.setMarketplaceConnected(false);
			return;
		}
		supplier.setMarketplaceConnected(supplier.getMarketplaceSupplierId() != null && !supplier.getMarketplaceSupplierId().isBlank());
	}

	private boolean isVisible(TenantScopedEntity entity) {
		return !(entity instanceof SoftDeletable softDeletable) || !Boolean.TRUE.equals(softDeletable.getDeleted());
	}

	private void touch(TenantScopedEntity entity) {
		if (entity instanceof HasTimestamp hasTimestamp) {
			hasTimestamp.setTimestamp(System.currentTimeMillis());
		}
	}
}
