package com.qaldrin.pos.exception;

public class TenantAccessException extends RuntimeException {

	public TenantAccessException(String message) {
		super(message);
	}
}
