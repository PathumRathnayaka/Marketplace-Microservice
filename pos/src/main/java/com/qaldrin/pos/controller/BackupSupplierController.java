package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupSupplier;
import com.qaldrin.pos.repository.BackupSupplierRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/suppliers")
public class BackupSupplierController extends AbstractTenantCrudController<BackupSupplier> {

	public BackupSupplierController(BackupSupplierRepository repository, TenantCrudService service) {
		super("Supplier", repository, service);
	}
}
