package com.cloudpos.supplier.mapper;

import com.cloudpos.supplier.dto.ProductResponse;
import com.cloudpos.supplier.entity.SupplierProduct;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

	public ProductResponse toResponse(SupplierProduct product) {
		return ProductResponse.builder()
				.id(product.getId())
				.supplierId(product.getSupplierId())
				.productName(product.getProductName())
				.brand(product.getBrand())
				.categoryName(product.getCategoryName())
				.unitType(product.getUnitType())
				.minimumOrderQty(product.getMinimumOrderQty())
				.availableStock(product.getAvailableStock())
				.price(product.getPrice())
				.description(product.getDescription())
				.imageUrl(product.getImageUrl())
				.available(product.isAvailable())
				.createdAt(product.getCreatedAt())
				.updatedAt(product.getUpdatedAt())
				.build();
	}
}
