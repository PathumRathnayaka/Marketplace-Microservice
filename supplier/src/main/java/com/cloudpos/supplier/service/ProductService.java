package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.ProductRequest;
import com.cloudpos.supplier.dto.ProductResponse;
import java.util.List;

public interface ProductService {

	ProductResponse create(ProductRequest request);

	List<ProductResponse> getMyProducts();

	ProductResponse update(String id, ProductRequest request);

	void delete(String id);

	List<ProductResponse> getPublicProducts();
}
