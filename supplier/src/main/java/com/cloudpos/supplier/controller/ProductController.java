package com.cloudpos.supplier.controller;

import com.cloudpos.supplier.dto.ApiResponse;
import com.cloudpos.supplier.dto.ProductRequest;
import com.cloudpos.supplier.dto.ProductResponse;
import com.cloudpos.supplier.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ApiResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Product created successfully", productService.create(request)));
	}

	@GetMapping("/my-products")
	public ResponseEntity<ApiResponse<List<ProductResponse>>> getMyProducts() {
		return ResponseEntity.ok(ApiResponse.success("Products fetched successfully", productService.getMyProducts()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponse>> update(
			@PathVariable String id,
			@Valid @RequestBody ProductRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Product updated successfully", productService.update(id, request)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
		productService.delete(id);
		return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
	}

	@GetMapping("/public")
	public ResponseEntity<ApiResponse<List<ProductResponse>>> getPublicProducts() {
		return ResponseEntity.ok(ApiResponse.success("Public products fetched successfully",
				productService.getPublicProducts()));
	}
}
