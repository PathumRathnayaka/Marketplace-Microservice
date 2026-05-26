package com.qaldrin.pos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiError.of(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
	}

	@ExceptionHandler(TenantAccessException.class)
	public ResponseEntity<ApiError> handleTenantAccess(TenantAccessException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ApiError.of(HttpStatus.FORBIDDEN.value(), exception.getMessage()));
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, MissingRequestHeaderException.class, IllegalArgumentException.class })
	public ResponseEntity<ApiError> handleBadRequest(Exception exception) {
		return ResponseEntity.badRequest()
				.body(ApiError.of(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
	}
}
