package com.qaldrin.pos.entity;

public interface TenantScopedEntity {

	String getId();

	void setId(String id);

	String getTenantId();

	void setTenantId(String tenantId);
}
