package com.cloudpos.gateway.exception;

import java.net.ConnectException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.cloudpos.gateway.filter.RequestLoggingFilter;
import com.cloudpos.gateway.util.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private final ObjectMapper objectMapper;

	public GlobalExceptionHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		HttpStatus status = resolveStatus(ex);
		String message = resolveMessage(status, ex);
		String requestId = exchange.getAttributeOrDefault(RequestLoggingFilter.REQUEST_ID_ATTRIBUTE, "unknown");

		log.error("[{}] Gateway error {}: {}", requestId, status, ex.getMessage(), ex);

		exchange.getResponse().setStatusCode(status);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		ApiResponse<Void> response = ApiResponse.error(message, LocalDateTime.now());

		try {
			byte[] bytes = objectMapper.writeValueAsBytes(response);
			DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
			return exchange.getResponse().writeWith(Mono.just(buffer));
		} catch (Exception serializationException) {
			log.error("[{}] Failed to serialize error response", requestId, serializationException);
			return exchange.getResponse().setComplete();
		}
	}

	private HttpStatus resolveStatus(Throwable ex) {
		if (ex instanceof NotFoundException || ex instanceof ResponseStatusException responseStatusException
				&& responseStatusException.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
			return HttpStatus.NOT_FOUND;
		}

		if (ex instanceof ConnectException || containsCause(ex, ConnectException.class)) {
			return HttpStatus.SERVICE_UNAVAILABLE;
		}

		if (ex instanceof ResponseStatusException responseStatusException) {
			return HttpStatus.valueOf(responseStatusException.getStatusCode().value());
		}

		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	private String resolveMessage(HttpStatus status, Throwable ex) {
		return switch (status) {
			case NOT_FOUND -> "Route not found";
			case SERVICE_UNAVAILABLE -> "Service unavailable";
			default -> ex.getMessage() == null || ex.getMessage().isBlank()
					? "Internal server error"
					: "Internal server error";
		};
	}

	private boolean containsCause(Throwable ex, Class<? extends Throwable> causeType) {
		Throwable current = ex;
		while (current != null) {
			if (causeType.isInstance(current)) {
				return true;
			}
			current = current.getCause();
		}
		return false;
	}
}
