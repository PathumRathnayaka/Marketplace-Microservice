package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.SupplierResponse;
import com.cloudpos.supplier.dto.SupplierUpdateRequest;
import com.cloudpos.supplier.entity.Supplier;
import com.cloudpos.supplier.exception.DuplicateResourceException;
import com.cloudpos.supplier.exception.ResourceNotFoundException;
import com.cloudpos.supplier.mapper.SupplierMapper;
import com.cloudpos.supplier.repository.SupplierRepository;
import com.cloudpos.supplier.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

	private final SupplierRepository supplierRepository;
	private final SupplierMapper supplierMapper;

	@Override
	public SupplierResponse getProfile() {
		return supplierMapper.toResponse(getCurrentSupplier());
	}

	@Override
	@Transactional
	public SupplierResponse updateProfile(SupplierUpdateRequest request) {
		Supplier supplier = getCurrentSupplier();
		String email = request.getEmail().trim().toLowerCase();
		if (!supplier.getEmail().equalsIgnoreCase(email) && supplierRepository.existsByEmail(email)) {
			throw new DuplicateResourceException("Email is already registered");
		}
		supplier.setBusinessName(request.getBusinessName());
		supplier.setOwnerName(request.getOwnerName());
		supplier.setEmail(email);
		supplier.setPhone(request.getPhone());
		supplier.setBusinessType(request.getBusinessType());
		supplier.setDistrict(request.getDistrict());
		supplier.setCity(request.getCity());
		supplier.setAddress(request.getAddress());
		supplier.setProfileImage(request.getProfileImage());
		return supplierMapper.toResponse(supplierRepository.save(supplier));
	}

	@Override
	@Transactional
	public void deleteProfile() {
		Supplier supplier = getCurrentSupplier();
		supplier.setActive(false);
		supplier.setDeleted(true);
		supplierRepository.save(supplier);
	}

	private Supplier getCurrentSupplier() {
		return supplierRepository.findByIdAndDeletedFalse(SecurityUtil.currentSupplierId())
				.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
	}
}
