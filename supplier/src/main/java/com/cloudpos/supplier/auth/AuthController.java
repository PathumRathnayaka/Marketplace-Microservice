package com.cloudpos.supplier.auth;

import com.cloudpos.supplier.dto.ApiResponse;
import com.cloudpos.supplier.dto.AuthResponse;
import com.cloudpos.supplier.dto.LoginRequest;
import com.cloudpos.supplier.dto.RegisterRequest;
import com.cloudpos.supplier.dto.SupplierResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Supplier registered successfully", authService.register(request)));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Login successful", authService.login(request)));
	}

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<SupplierResponse>> me() {
		return ResponseEntity.ok(ApiResponse.success("Supplier profile fetched successfully", authService.me()));
	}
}
