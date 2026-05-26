package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.SupplierResponse;
import com.cloudpos.supplier.dto.SupplierUpdateRequest;

public interface SupplierService {

	SupplierResponse getProfile();

	SupplierResponse updateProfile(SupplierUpdateRequest request);

	void deleteProfile();
}
