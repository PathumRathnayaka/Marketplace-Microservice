package com.cloudpos.supplier.mapper;

import com.cloudpos.supplier.dto.CategoryResponse;
import com.cloudpos.supplier.entity.SupplierCategory;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

	public CategoryResponse toResponse(SupplierCategory category) {
		return CategoryResponse.builder()
				.id(category.getId())
				.supplierId(category.getSupplierId())
				.categoryName(category.getCategoryName())
				.createdAt(category.getCreatedAt())
				.build();
	}
}
