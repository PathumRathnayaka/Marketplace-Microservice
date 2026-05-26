package com.cloudpos.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

	public static final String REQUEST_ID_HEADER = "X-Request-Id";
	public static final String REQUEST_ID_ATTRIBUTE = "requestId";

	private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String requestId = resolveRequestId(exchange.getRequest());
		long startTime = System.currentTimeMillis();

		ServerHttpRequest request = exchange.getRequest()
				.mutate()
				.header(REQUEST_ID_HEADER, requestId)
				.build();

		ServerWebExchange mutatedExchange = exchange.mutate().request(request).build();
		mutatedExchange.getAttributes().put(REQUEST_ID_ATTRIBUTE, requestId);
		mutatedExchange.getResponse().getHeaders().set(REQUEST_ID_HEADER, requestId);

		log.info("[{}] {} {}", requestId, request.getMethod(), request.getURI().getPath());

		return chain.filter(mutatedExchange)
				.doFinally(signalType -> {
					long duration = System.currentTimeMillis() - startTime;
					log.info("[{}] Response {} {} - {} ms",
							requestId,
							mutatedExchange.getResponse().getStatusCode(),
							request.getURI().getPath(),
							duration);
				});
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	private String resolveRequestId(ServerHttpRequest request) {
		String existingRequestId = request.getHeaders().getFirst(REQUEST_ID_HEADER);
		if (existingRequestId == null || existingRequestId.isBlank()) {
			return "unknown";
		}
		return existingRequestId;
	}
}
