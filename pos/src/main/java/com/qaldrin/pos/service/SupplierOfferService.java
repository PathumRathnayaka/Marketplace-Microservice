package com.qaldrin.pos.service;

import com.qaldrin.pos.entity.LowStockStatus;
import com.qaldrin.pos.entity.SupplierOffer;
import com.qaldrin.pos.entity.SupplierOfferStatus;
import com.qaldrin.pos.exception.ResourceNotFoundException;
import com.qaldrin.pos.repository.LowStockRequestRepository;
import com.qaldrin.pos.repository.SupplierOfferRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierOfferService {

	private final SupplierOfferRepository supplierOfferRepository;
	private final LowStockRequestRepository lowStockRequestRepository;

	public SupplierOfferService(SupplierOfferRepository supplierOfferRepository, LowStockRequestRepository lowStockRequestRepository) {
		this.supplierOfferRepository = supplierOfferRepository;
		this.lowStockRequestRepository = lowStockRequestRepository;
	}

	@Transactional(readOnly = true)
	public List<SupplierOffer> listByRequest(String tenantId, String requestId) {
		return supplierOfferRepository.findByTenantIdAndRequestIdAndDeletedFalse(tenantId, requestId);
	}

	@Transactional
	public SupplierOffer create(String tenantId, SupplierOffer offer) {
		if (offer.getRequestId() == null || offer.getRequestId().isBlank()) {
			throw new IllegalArgumentException("requestId is required");
		}
		lowStockRequestRepository.findByIdAndTenantId(offer.getRequestId(), tenantId)
				.filter(request -> !Boolean.TRUE.equals(request.getDeleted()))
				.orElseThrow(() -> new ResourceNotFoundException("Low stock request not found"));
		offer.setTenantId(tenantId);
		if (offer.getId() == null || offer.getId().isBlank()) {
			offer.setId(UUID.randomUUID().toString());
		}
		if (offer.getStatus() == null) {
			offer.setStatus(SupplierOfferStatus.PENDING);
		}
		if (offer.getDeleted() == null) {
			offer.setDeleted(false);
		}
		if (offer.getCreatedAt() == null) {
			offer.setCreatedAt(LocalDateTime.now());
		}
		offer.setUpdatedAt(LocalDateTime.now());
		return supplierOfferRepository.save(offer);
	}

	@Transactional
	public SupplierOffer accept(String tenantId, String id) {
		SupplierOffer offer = findOffer(tenantId, id);
		offer.setStatus(SupplierOfferStatus.ACCEPTED);
		offer.setUpdatedAt(LocalDateTime.now());
		lowStockRequestRepository.findByIdAndTenantId(offer.getRequestId(), tenantId)
				.ifPresent(request -> {
					request.setStatus(LowStockStatus.PENDING);
					request.setUpdatedAt(LocalDateTime.now());
					lowStockRequestRepository.save(request);
				});
		return supplierOfferRepository.save(offer);
	}

	@Transactional
	public SupplierOffer reject(String tenantId, String id) {
		SupplierOffer offer = findOffer(tenantId, id);
		offer.setStatus(SupplierOfferStatus.REJECTED);
		offer.setUpdatedAt(LocalDateTime.now());
		return supplierOfferRepository.save(offer);
	}

	private SupplierOffer findOffer(String tenantId, String id) {
		return supplierOfferRepository.findByIdAndTenantId(id, tenantId)
				.filter(offer -> !Boolean.TRUE.equals(offer.getDeleted()))
				.orElseThrow(() -> new ResourceNotFoundException("Supplier offer not found"));
	}
}
