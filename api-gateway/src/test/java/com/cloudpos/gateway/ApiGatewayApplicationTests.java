package com.cloudpos.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.cloudpos.gateway.filter.RequestLoggingFilter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	void healthEndpointReturnsGatewayStatus() {
		webTestClient.get()
				.uri("/gateway/health")
				.exchange()
				.expectStatus().isOk()
				.expectHeader().exists(RequestLoggingFilter.REQUEST_ID_HEADER)
				.expectBody()
				.jsonPath("$.status").isEqualTo("UP")
				.jsonPath("$.service").isEqualTo("API Gateway");
	}

	@Test
	void corsPreflightAllowsFrontendOrigin() {
		webTestClient.options()
				.uri("/api/pos/products")
				.header(HttpHeaders.ORIGIN, "http://localhost:3000")
				.header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
				.exchange()
				.expectStatus().isOk()
				.expectHeader().valueEquals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000")
				.expectHeader().valueEquals(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
	}
}
