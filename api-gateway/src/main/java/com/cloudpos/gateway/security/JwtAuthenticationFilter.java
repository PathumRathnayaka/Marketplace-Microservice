package com.cloudpos.gateway.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.cloudpos.gateway.filter.RequestLoggingFilter;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private static final String BEARER_PREFIX = "Bearer ";
	private static final List<String> PUBLIC_ROUTES = List.of(
			"/api/auth/",
			"/api/supplier/public/");

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();
		String requestId = exchange.getAttributeOrDefault(RequestLoggingFilter.REQUEST_ID_ATTRIBUTE, "unknown");

		if (isPublicRoute(path)) {
			return chain.filter(exchange);
		}

		String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader == null || authorizationHeader.isBlank()) {
			log.debug("[{}] Missing Authorization header for protected route preparation: {}", requestId, path);
			return chain.filter(exchange);
		}

		if (!authorizationHeader.startsWith(BEARER_PREFIX) || authorizationHeader.length() <= BEARER_PREFIX.length()) {
			log.warn("[{}] Invalid Bearer token format for request path: {}", requestId, path);
			return chain.filter(exchange);
		}

		String token = authorizationHeader.substring(BEARER_PREFIX.length());
		exchange.getAttributes().put("jwtToken", token);
		log.debug("[{}] Bearer token extracted for future JWT validation", requestId);

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 10;
	}

	private boolean isPublicRoute(String path) {
		return PUBLIC_ROUTES.stream().anyMatch(path::startsWith);
	}
}
