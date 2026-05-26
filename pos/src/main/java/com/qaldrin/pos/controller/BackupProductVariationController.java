package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupProductVariation;
import com.qaldrin.pos.repository.BackupProductVariationRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-variations")
public class BackupProductVariationController extends AbstractTenantCrudController<BackupProductVariation> {

	public BackupProductVariationController(BackupProductVariationRepository repository, TenantCrudService service) {
		super("Product variation", repository, service);
	}
}
