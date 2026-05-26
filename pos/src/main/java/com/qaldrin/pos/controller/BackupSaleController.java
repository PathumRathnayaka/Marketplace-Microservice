package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupSale;
import com.qaldrin.pos.repository.BackupSaleRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
public class BackupSaleController extends AbstractTenantCrudController<BackupSale> {

	public BackupSaleController(BackupSaleRepository repository, TenantCrudService service) {
		super("Sale", repository, service);
	}
}
