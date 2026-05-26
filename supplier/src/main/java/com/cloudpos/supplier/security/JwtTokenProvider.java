package com.cloudpos.supplier.security;

/**
 * @deprecated Token generation has been moved to auth-service as part of auth
 *             centralization.
 *             This class is intentionally empty and will be removed in the next
 *             cleanup pass.
 *             All JWT validation is now handled by
 *             {@link com.cloudpos.common.security.JwtUtil}.
 */
@Deprecated(since = "2.0", forRemoval = true)
public class JwtTokenProvider {
	// Removed — all token issuance is delegated to auth-service.
	// JWT validation is handled by JwtAuthenticationFilter via common-library
	// JwtUtil.
}
