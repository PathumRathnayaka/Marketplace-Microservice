package com.cloudpos.supplier.security;

import com.cloudpos.common.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Stateless JWT validation filter.
 * Validates tokens issued by auth-service using the shared secret via
 * common-library JwtUtil.
 * Does NOT generate tokens — token generation is the sole responsibility of
 * auth-service.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final String jwtSecret;

	public JwtAuthenticationFilter(@Value("${app.jwt.secret}") String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extractToken(request);
		if (StringUtils.hasText(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				JwtUtil.validateToken(token, jwtSecret);
				String email = JwtUtil.extractEmail(token, jwtSecret);
				String role = JwtUtil.extractRole(token, jwtSecret);
				String userId = JwtUtil.extractUserId(token, jwtSecret);
				String tenantId = JwtUtil.extractTenantId(token, jwtSecret);

				List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
				// Create a SupplierPrincipal from JWT claims to satisfy SecurityUtil checks
				SupplierPrincipal principal = new SupplierPrincipal(userId, email, "", true);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
						null, authorities);
				// Attach extra claims so downstream controllers can access them
				authentication.setDetails(new SupplierAuthDetails(userId, tenantId, role));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (RuntimeException ex) {
				log.debug("JWT validation failed: {}", ex.getMessage());
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
