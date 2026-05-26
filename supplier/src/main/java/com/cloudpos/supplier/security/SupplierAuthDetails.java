package com.cloudpos.supplier.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Carries extra JWT claims (userId, tenantId, role) as Spring Security
 * authentication details.
 * Downstream controllers can cast authentication.getDetails() to this class.
 */
@Getter
@RequiredArgsConstructor
public class SupplierAuthDetails {
    private final String userId;
    private final String tenantId;
    private final String role;
}
