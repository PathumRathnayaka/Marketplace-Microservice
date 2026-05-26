package com.qaldrin.pos.controller;

import com.qaldrin.pos.common.TenantHeader;
import com.qaldrin.pos.entity.Tenant;
import com.qaldrin.pos.exception.ResourceNotFoundException;
import com.qaldrin.pos.exception.TenantAccessException;
import com.qaldrin.pos.repository.TenantRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {

	private final TenantRepository repository;

	public TenantController(TenantRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public ResponseEntity<Tenant> create(@Valid @RequestBody Tenant request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(request));
	}

	@GetMapping
	public List<Tenant> listCurrentTenant(@RequestHeader(TenantHeader.NAME) String tenantId) {
		return List.of(findCurrentTenant(tenantId));
	}

	@GetMapping("/me")
	public Tenant me(@RequestHeader(TenantHeader.NAME) String tenantId) {
		return findCurrentTenant(tenantId);
	}

	@GetMapping("/{id}")
	public Tenant get(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id) {
		requireSameTenant(tenantId, id);
		return findCurrentTenant(tenantId);
	}

	@PutMapping("/{id}")
	public Tenant update(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id, @Valid @RequestBody Tenant request) {
		requireSameTenant(tenantId, id);
		findCurrentTenant(tenantId);
		request.setId(id);
		return repository.save(request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id) {
		requireSameTenant(tenantId, id);
		Tenant tenant = findCurrentTenant(tenantId);
		repository.delete(tenant);
		return ResponseEntity.noContent().build();
	}

	private Tenant findCurrentTenant(String tenantId) {
		return repository.findById(tenantId)
				.orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
	}

	private void requireSameTenant(String tenantId, String requestedTenantId) {
		if (!tenantId.equals(requestedTenantId)) {
			throw new TenantAccessException("Cannot access another tenant");
		}
	}
}
