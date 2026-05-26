package com.qaldrin.pos.controller;

import com.qaldrin.pos.common.TenantHeader;
import com.qaldrin.pos.entity.TenantScopedEntity;
import com.qaldrin.pos.repository.TenantScopedRepository;
import com.qaldrin.pos.service.TenantCrudService;
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

public abstract class AbstractTenantCrudController<T extends TenantScopedEntity> {

	private final String resourceName;
	private final TenantScopedRepository<T> repository;
	private final TenantCrudService service;

	protected AbstractTenantCrudController(String resourceName, TenantScopedRepository<T> repository, TenantCrudService service) {
		this.resourceName = resourceName;
		this.repository = repository;
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<T> create(@RequestHeader(TenantHeader.NAME) String tenantId, @Valid @RequestBody T request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.create(resourceName, tenantId, request, repository));
	}

	@GetMapping
	public List<T> list(@RequestHeader(TenantHeader.NAME) String tenantId) {
		return service.list(tenantId, repository);
	}

	@GetMapping("/{id}")
	public T get(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id) {
		return service.get(resourceName, tenantId, id, repository);
	}

	@PutMapping("/{id}")
	public T update(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id, @Valid @RequestBody T request) {
		return service.update(resourceName, tenantId, id, request, repository);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@RequestHeader(TenantHeader.NAME) String tenantId, @PathVariable String id) {
		service.delete(resourceName, tenantId, id, repository);
		return ResponseEntity.noContent().build();
	}
}
