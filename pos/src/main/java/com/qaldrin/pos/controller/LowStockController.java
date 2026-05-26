package com.qaldrin.pos.controller;

import com.qaldrin.pos.common.TenantHeader;
import com.qaldrin.pos.entity.LowStockRequest;
import com.qaldrin.pos.service.LowStockService;
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
@RequestMapping("/api/low-stock")
public class LowStockController {

	private final LowStockService service;

	public LowStockController(LowStockService service) {
		this.service = service;
	}

	@GetMapping
	public List<LowStockRequest> list(@RequestHeader(TenantHeader.NAME) String tenantId) {
		return service.list(tenantId);
	}

	@GetMapping("/open")
	public List<LowStockRequest> listOpen(@RequestHeader(TenantHeader.NAME) String tenantId) {
		return service.listOpen(tenantId);
	}

	@PostMapping("/create")
	public ResponseEntity<LowStockRequest> create(@RequestHeader(TenantHeader.NAME) String tenantId, @Valid @RequestBody LowStockRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(tenantId, request));
	}

	@PutMapping("/{id}/cancel")
	public LowStockRequest cancel(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id) {
		return service.cancel(tenantId, id);
	}
}
