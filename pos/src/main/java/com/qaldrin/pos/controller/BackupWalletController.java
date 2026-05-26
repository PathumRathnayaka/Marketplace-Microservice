package com.qaldrin.pos.controller;

import com.qaldrin.pos.entity.BackupWallet;
import com.qaldrin.pos.repository.BackupWalletRepository;
import com.qaldrin.pos.service.TenantCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallets")
public class BackupWalletController extends AbstractTenantCrudController<BackupWallet> {

	public BackupWalletController(BackupWalletRepository repository, TenantCrudService service) {
		super("Wallet", repository, service);
	}
}
