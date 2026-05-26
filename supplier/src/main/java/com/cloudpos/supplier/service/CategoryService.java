package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.CategoryRequest;
import com.cloudpos.supplier.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {

	CategoryResponse create(CategoryRequest request);

	List<CategoryResponse> getMyCategories();

	void delete(String id);
}
