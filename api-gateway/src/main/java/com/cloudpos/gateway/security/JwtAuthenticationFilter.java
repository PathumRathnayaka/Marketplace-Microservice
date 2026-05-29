package com.cloudpos.gateway.security;

import java.util.List;

import com.cloudpos.gateway.filter.RequestLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.cloudpos.common.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private static final String BEARER_PREFIX = "Bearer ";
	private static final List<String> PUBLIC_ROUTES = List.of(
			"/api/auth",
			"/api/supplier/public/");

	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();
		String requestId = exchange.getAttributeOrDefault(RequestLoggingFilter.REQUEST_ID_ATTRIBUTE, "unknown");

		if (isPublicRoute(path)) {
			return chain.filter(exchange);
		}

		String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader == null || authorizationHeader.isBlank()
				|| !authorizationHeader.startsWith(BEARER_PREFIX)) {
			log.debug("[{}] Missing or invalid Authorization header for protected route: {}", requestId, path);
			return chain.filter(exchange);
		}

		String token = authorizationHeader.substring(BEARER_PREFIX.length());

		try {
			// Extract claims using common-library JwtUtil
			String email = JwtUtil.extractEmail(token, jwtSecret);
			String role = JwtUtil.extractRole(token, jwtSecret);
			String userId = JwtUtil.extractUserId(token, jwtSecret);
			String tenantId = JwtUtil.extractTenantId(token, jwtSecret);

			log.debug("[{}] Valid JWT for user: {}, role: {}, tenant: {}", requestId, email, role, tenantId);

			// Inject internal headers for downstream microservices (overwrites any
			// client-provided headers)
			ServerHttpRequest request = exchange.getRequest().mutate()
					.headers(httpHeaders -> {
						httpHeaders.set("X-User-Id", userId);
						httpHeaders.set("X-User-Role", role);
						httpHeaders.set("X-Tenant-Id", tenantId);
						httpHeaders.set("X-User-Email", email);
					})
					.build();

			return chain.filter(exchange.mutate().request(request).build());

		} catch (Exception e) {
			log.error("[{}] JWT validation failed: {}", requestId, e.getMessage());
			return chain.filter(exchange);
		}
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 10;
	}

	private boolean isPublicRoute(String path) {
		return PUBLIC_ROUTES.stream().anyMatch(path::startsWith);
	}
}
