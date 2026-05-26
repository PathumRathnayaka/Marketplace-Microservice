package com.cloudpos.gateway.util;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
		boolean success,
		String message,
		T data,
		LocalDateTime timestamp) {

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data, LocalDateTime.now());
	}

	public static <T> ApiResponse<T> error(String message, LocalDateTime timestamp) {
		return new ApiResponse<>(false, message, null, timestamp);
	}
}
