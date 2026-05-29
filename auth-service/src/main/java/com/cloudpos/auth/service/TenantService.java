package com.cloudpos.auth.service;

import com.cloudpos.auth.entity.Tenant;
import com.cloudpos.auth.entity.TenantStatus;
import com.cloudpos.auth.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    @Transactional
    public Tenant createTenant(String businessName, String ownerEmail) {
        if (tenantRepository.existsByOwnerEmail(ownerEmail)) {
            throw new RuntimeException("Tenant already exists for this owner email");
        }

        Tenant tenant = Tenant.builder()
                .businessName(businessName)
                .ownerEmail(ownerEmail)
                .status(TenantStatus.ACTIVE)
                .build();

        return tenantRepository.save(tenant);
    }
}
