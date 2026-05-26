package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupProductReturn;
import com.qaldrin.pos.repository.BackupProductReturnRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-returns")
public class BackupProductReturnController extends AbstractTenantCrudController<BackupProductReturn> {

	public BackupProductReturnController(BackupProductReturnRepository repository, TenantCrudService service) {
		super("Product return", repository, service);
	}
}
