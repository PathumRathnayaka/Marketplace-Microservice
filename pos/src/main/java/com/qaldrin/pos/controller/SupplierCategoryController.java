package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.SupplierCategory;
import com.qaldrin.pos.repository.SupplierCategoryRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/supplier-categories")
public class SupplierCategoryController extends AbstractTenantCrudController<SupplierCategory> {

	public SupplierCategoryController(SupplierCategoryRepository repository, TenantCrudService service) {
		super("Supplier category", repository, service);
	}
}
