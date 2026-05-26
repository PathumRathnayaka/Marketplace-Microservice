package com.cloudpos.supplier.controller;

import com.cloudpos.supplier.dto.ApiResponse;
import com.cloudpos.supplier.dto.CategoryRequest;
import com.cloudpos.supplier.dto.CategoryResponse;
import com.cloudpos.supplier.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Category created successfully", categoryService.create(request)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<CategoryResponse>>> getMyCategories() {
		return ResponseEntity.ok(ApiResponse.success("Categories fetched successfully", categoryService.getMyCategories()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
		categoryService.delete(id);
		return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
	}
}
