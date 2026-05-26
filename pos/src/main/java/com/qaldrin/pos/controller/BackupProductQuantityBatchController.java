package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupProductQuantityBatch;
import com.qaldrin.pos.repository.BackupProductQuantityBatchRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-quantity-batches")
public class BackupProductQuantityBatchController extends AbstractTenantCrudController<BackupProductQuantityBatch> {

	public BackupProductQuantityBatchController(BackupProductQuantityBatchRepository repository, TenantCrudService service) {
		super("Product quantity batch", repository, service);
	}
}
