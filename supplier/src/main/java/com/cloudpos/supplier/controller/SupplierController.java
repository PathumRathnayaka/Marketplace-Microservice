package com.cloudpos.supplier.controller;

import com.cloudpos.supplier.dto.ApiResponse;
import com.cloudpos.supplier.dto.SupplierResponse;
import com.cloudpos.supplier.dto.SupplierUpdateRequest;
import com.cloudpos.supplier.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

	private final SupplierService supplierService;

	@GetMapping("/profile")
	public ResponseEntity<ApiResponse<SupplierResponse>> getProfile() {
		return ResponseEntity.ok(ApiResponse.success("Supplier profile fetched successfully", supplierService.getProfile()));
	}

	@PutMapping("/profile")
	public ResponseEntity<ApiResponse<SupplierResponse>> updateProfile(
			@Valid @RequestBody SupplierUpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Supplier profile updated successfully",
				supplierService.updateProfile(request)));
	}

	@DeleteMapping("/profile")
	public ResponseEntity<ApiResponse<Void>> deleteProfile() {
		supplierService.deleteProfile();
		return ResponseEntity.ok(ApiResponse.success("Supplier profile deleted successfully", null));
	}
}
