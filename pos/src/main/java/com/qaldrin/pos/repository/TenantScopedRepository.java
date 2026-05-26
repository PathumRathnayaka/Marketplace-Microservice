package com.qaldrin.pos.repository;

import com.qaldrin.pos.entity.TenantScopedEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TenantScopedRepository<T extends TenantScopedEntity> extends JpaRepository<T, String> {

	List<T> findByTenantId(String tenantId);

	Optional<T> findByIdAndTenantId(String id, String tenantId);
}
