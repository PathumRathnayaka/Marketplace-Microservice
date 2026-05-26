package com.cloudpos.gateway.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/gateway/health")
	public Map<String, String> health() {
		return Map.of(
				"status", "UP",
				"service", "API Gateway");
	}
}
