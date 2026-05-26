package com.cloudpos.supplier.util;

import com.cloudpos.supplier.exception.UnauthorizedException;
import com.cloudpos.supplier.security.SupplierPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

	private SecurityUtil() {
	}

	public static SupplierPrincipal currentPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| !(authentication.getPrincipal() instanceof SupplierPrincipal principal)) {
			throw new UnauthorizedException("Authentication is required");
		}
		return principal;
	}

	public static String currentSupplierId() {
		return currentPrincipal().getId();
	}
}
