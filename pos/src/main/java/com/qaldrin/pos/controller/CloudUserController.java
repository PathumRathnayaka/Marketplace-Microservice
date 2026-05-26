package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.CloudUser;
import com.qaldrin.pos.repository.CloudUserRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cloud-users")
public class CloudUserController extends AbstractTenantCrudController<CloudUser> {

	public CloudUserController(CloudUserRepository repository, TenantCrudService service) {
		super("Cloud user", repository, service);
	}
}
