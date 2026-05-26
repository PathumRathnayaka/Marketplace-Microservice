package com.qaldrin.pos.controller;

import com.qaldrin.pos.common.TenantHeader;
import com.qaldrin.pos.entity.SupplierOffer;
import com.qaldrin.pos.service.SupplierOfferService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supplier-offers")
public class SupplierOfferController {

	private final SupplierOfferService service;

	public SupplierOfferController(SupplierOfferService service) {
		this.service = service;
	}

	@GetMapping("/request/{requestId}")
	public List<SupplierOffer> listByRequest(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String requestId) {
		return service.listByRequest(tenantId, requestId);
	}

	@PostMapping
	public ResponseEntity<SupplierOffer> create(@RequestHeader(TenantHeader.NAME) String tenantId, @Valid @RequestBody SupplierOffer offer) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(tenantId, offer));
	}

	@PutMapping("/{id}/accept")
	public SupplierOffer accept(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id) {
		return service.accept(tenantId, id);
	}

	@PutMapping("/{id}/reject")
	public SupplierOffer reject(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id) {
		return service.reject(tenantId, id);
	}
}
