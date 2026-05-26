package com.qaldrin.pos.service;

import com.qaldrin.pos.entity.BackupProduct;
import com.qaldrin.pos.entity.LowStockRequest;
import com.qaldrin.pos.entity.LowStockStatus;
import com.qaldrin.pos.exception.ResourceNotFoundException;
import com.qaldrin.pos.integration.marketplace.MarketplaceEventPublisher;
import com.qaldrin.pos.repository.BackupProductQuantityBatchRepository;
import com.qaldrin.pos.repository.BackupProductRepository;
import com.qaldrin.pos.repository.LowStockRequestRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LowStockService {

	private final LowStockRequestRepository lowStockRequestRepository;
	private final BackupProductRepository productRepository;
	private final BackupProductQuantityBatchRepository quantityBatchRepository;
	private final MarketplaceEventPublisher marketplaceEventPublisher;

	public LowStockService(
			LowStockRequestRepository lowStockRequestRepository,
			BackupProductRepository productRepository,
			BackupProductQuantityBatchRepository quantityBatchRepository,
			MarketplaceEventPublisher marketplaceEventPublisher) {
		this.lowStockRequestRepository = lowStockRequestRepository;
		this.productRepository = productRepository;
		this.quantityBatchRepository = quantityBatchRepository;
		this.marketplaceEventPublisher = marketplaceEventPublisher;
	}

	@Transactional(readOnly = true)
	public List<LowStockRequest> list(String tenantId) {
		return lowStockRequestRepository.findByTenantId(tenantId).stream()
				.filter(request -> !Boolean.TRUE.equals(request.getDeleted()))
				.toList();
	}

	@Transactional(readOnly = true)
	public List<LowStockRequest> listOpen(String tenantId) {
		return lowStockRequestRepository.findByTenantIdAndStatusAndDeletedFalse(tenantId, LowStockStatus.OPEN);
	}

	@Transactional
	public LowStockRequest create(String tenantId, LowStockRequest request) {
		request.setTenantId(tenantId);
		if (request.getProductId() == null || request.getProductId().isBlank()) {
			throw new IllegalArgumentException("productId is required");
		}
		if (request.getStatus() == null || request.getStatus() == LowStockStatus.OPEN) {
			var existingOpen = lowStockRequestRepository.findFirstByTenantIdAndProductIdAndStatusAndDeletedFalse(
					tenantId,
					request.getProductId(),
					LowStockStatus.OPEN
			);
			if (existingOpen.isPresent()) {
				return existingOpen.get();
			}
		}
		if (request.getId() == null || request.getId().isBlank()) {
			request.setId(UUID.randomUUID().toString());
		}
		if (request.getStatus() == null) {
			request.setStatus(LowStockStatus.OPEN);
		}
		if (request.getDeleted() == null) {
			request.setDeleted(false);
		}
		if (request.getCreatedAt() == null) {
			request.setCreatedAt(LocalDateTime.now());
		}
		request.setUpdatedAt(LocalDateTime.now());
		LowStockRequest saved = lowStockRequestRepository.save(request);
		marketplaceEventPublisher.publishLowStockCreatedEvent(saved);
		return saved;
	}

	@Transactional
	public LowStockRequest cancel(String tenantId, String id) {
		LowStockRequest request = lowStockRequestRepository.findByIdAndTenantId(id, tenantId)
				.filter(existing -> !Boolean.TRUE.equals(existing.getDeleted()))
				.orElseThrow(() -> new ResourceNotFoundException("Low stock request not found"));
		request.setStatus(LowStockStatus.CANCELLED);
		request.setUpdatedAt(LocalDateTime.now());
		return lowStockRequestRepository.save(request);
	}

	@Scheduled(
			initialDelayString = "${pos.low-stock.initial-delay-ms:60000}",
			fixedDelayString = "${pos.low-stock.scan-delay-ms:300000}"
	)
	@Transactional
	public void detectLowStock() {
		productRepository.findByMinimumQuantityIsNotNull().stream()
				.filter(product -> !Boolean.TRUE.equals(product.getDeleted()))
				.forEach(this::createRequestIfLowStock);
	}

	private void createRequestIfLowStock(BackupProduct product) {
		BigDecimal currentQuantity = quantityBatchRepository.sumQuantityByTenantAndProduct(product.getTenantId(), product.getId());
		if (currentQuantity.compareTo(product.getMinimumQuantity()) > 0) {
			return;
		}
		boolean duplicateOpen = lowStockRequestRepository.existsByTenantIdAndProductIdAndStatusAndDeletedFalse(
				product.getTenantId(),
				product.getId(),
				LowStockStatus.OPEN
		);
		if (duplicateOpen) {
			return;
		}
		LowStockRequest request = new LowStockRequest();
		request.setProductId(product.getId());
		request.setProductName(product.getName());
		request.setCategory(product.getCategory());
		request.setCurrentQuantity(currentQuantity);
		request.setMinimumQuantity(product.getMinimumQuantity());
		request.setRequiredQuantity(product.getMinimumQuantity().subtract(currentQuantity).max(BigDecimal.ZERO));
		create(product.getTenantId(), request);
	}
}
