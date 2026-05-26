package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.CategoryRequest;
import com.cloudpos.supplier.dto.CategoryResponse;
import com.cloudpos.supplier.entity.SupplierCategory;
import com.cloudpos.supplier.exception.ResourceNotFoundException;
import com.cloudpos.supplier.mapper.CategoryMapper;
import com.cloudpos.supplier.repository.SupplierCategoryRepository;
import com.cloudpos.supplier.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final SupplierCategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Override
	@Transactional
	public CategoryResponse create(CategoryRequest request) {
		SupplierCategory category = SupplierCategory.builder()
				.supplierId(SecurityUtil.currentSupplierId())
				.categoryName(request.getCategoryName())
				.build();
		return categoryMapper.toResponse(categoryRepository.save(category));
	}

	@Override
	public List<CategoryResponse> getMyCategories() {
		return categoryRepository.findBySupplierId(SecurityUtil.currentSupplierId()).stream()
				.map(categoryMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional
	public void delete(String id) {
		String supplierId = SecurityUtil.currentSupplierId();
		SupplierCategory category = categoryRepository.findByIdAndSupplierId(id, supplierId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		categoryRepository.delete(category);
	}
}
