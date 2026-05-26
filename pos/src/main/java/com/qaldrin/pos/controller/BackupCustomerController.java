package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupCustomer;
import com.qaldrin.pos.repository.BackupCustomerRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class BackupCustomerController extends AbstractTenantCrudController<BackupCustomer> {

	public BackupCustomerController(BackupCustomerRepository repository, TenantCrudService service) {
		super("Customer", repository, service);
	}
}
