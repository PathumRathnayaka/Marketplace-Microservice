package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupProduct;
import com.qaldrin.pos.repository.BackupProductRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class BackupProductController extends AbstractTenantCrudController<BackupProduct> {

	public BackupProductController(BackupProductRepository repository, TenantCrudService service) {
		super("Product", repository, service);
	}
}
