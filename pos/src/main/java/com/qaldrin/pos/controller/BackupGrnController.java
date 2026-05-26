package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupGrn;
import com.qaldrin.pos.repository.BackupGrnRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/grns")
public class BackupGrnController extends AbstractTenantCrudController<BackupGrn> {

	public BackupGrnController(BackupGrnRepository repository, TenantCrudService service) {
		super("GRN", repository, service);
	}
}
