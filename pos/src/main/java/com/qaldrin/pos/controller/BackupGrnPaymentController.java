package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupGrnPayment;
import com.qaldrin.pos.repository.BackupGrnPaymentRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/grn-payments")
public class BackupGrnPaymentController extends AbstractTenantCrudController<BackupGrnPayment> {

	public BackupGrnPaymentController(BackupGrnPaymentRepository repository, TenantCrudService service) {
		super("GRN payment", repository, service);
	}
}
