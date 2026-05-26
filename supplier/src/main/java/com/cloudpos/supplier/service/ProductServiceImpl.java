package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.ProductRequest;
import com.cloudpos.supplier.dto.ProductResponse;
import com.cloudpos.supplier.entity.SupplierProduct;
import com.cloudpos.supplier.exception.ResourceNotFoundException;
import com.cloudpos.supplier.mapper.ProductMapper;
import com.cloudpos.supplier.repository.SupplierProductRepository;
import com.cloudpos.supplier.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final SupplierProductRepository productRepository;
	private final ProductMapper productMapper;

	@Override
	@Transactional
	public ProductResponse create(ProductRequest request) {
		SupplierProduct product = SupplierProduct.builder()
				.supplierId(SecurityUtil.currentSupplierId())
				.productName(request.getProductName())
				.brand(request.getBrand())
				.categoryName(request.getCategoryName())
				.unitType(request.getUnitType())
				.minimumOrderQty(request.getMinimumOrderQty())
				.availableStock(request.getAvailableStock())
				.price(request.getPrice())
				.description(request.getDescription())
				.imageUrl(request.getImageUrl())
				.available(request.getAvailable() == null || request.getAvailable())
				.deleted(false)
				.build();
		return productMapper.toResponse(productRepository.save(product));
	}

	@Override
	public List<ProductResponse> getMyProducts() {
		return productRepository.findBySupplierIdAndDeletedFalse(SecurityUtil.currentSupplierId()).stream()
				.map(productMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional
	public ProductResponse update(String id, ProductRequest request) {
		SupplierProduct product = getOwnedProduct(id);
		product.setProductName(request.getProductName());
		product.setBrand(request.getBrand());
		product.setCategoryName(request.getCategoryName());
		product.setUnitType(request.getUnitType());
		product.setMinimumOrderQty(request.getMinimumOrderQty());
		product.setAvailableStock(request.getAvailableStock());
		product.setPrice(request.getPrice());
		product.setDescription(request.getDescription());
		product.setImageUrl(request.getImageUrl());
		product.setAvailable(request.getAvailable() == null || request.getAvailable());
		return productMapper.toResponse(productRepository.save(product));
	}

	@Override
	@Transactional
	public void delete(String id) {
		SupplierProduct product = getOwnedProduct(id);
		product.setDeleted(true);
		product.setAvailable(false);
		productRepository.save(product);
	}

	@Override
	public List<ProductResponse> getPublicProducts() {
		return productRepository.findByDeletedFalseAndAvailableTrue().stream()
				.map(productMapper::toResponse)
				.toList();
	}

	private SupplierProduct getOwnedProduct(String id) {
		return productRepository.findByIdAndSupplierIdAndDeletedFalse(id, SecurityUtil.currentSupplierId())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}
}
