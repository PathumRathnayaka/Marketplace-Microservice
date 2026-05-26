package com.cloudpos.gateway.filter;

import java.util.UUID;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class RequestCorrelationFilter implements WebFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String requestId = resolveRequestId(exchange);

		exchange.getAttributes().put(RequestLoggingFilter.REQUEST_ID_ATTRIBUTE, requestId);
		exchange.getResponse().getHeaders().set(RequestLoggingFilter.REQUEST_ID_HEADER, requestId);

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	private String resolveRequestId(ServerWebExchange exchange) {
		String existingRequestId = exchange.getRequest().getHeaders().getFirst(RequestLoggingFilter.REQUEST_ID_HEADER);
		if (existingRequestId == null || existingRequestId.isBlank()) {
			return UUID.randomUUID().toString();
		}
		return existingRequestId;
	}
}
