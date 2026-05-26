package com.cloudpos.supplier.mapper;

import com.cloudpos.supplier.dto.SupplierResponse;
import com.cloudpos.supplier.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

	public SupplierResponse toResponse(Supplier supplier) {
		return SupplierResponse.builder()
				.id(supplier.getId())
				.businessName(supplier.getBusinessName())
				.ownerName(supplier.getOwnerName())
				.email(supplier.getEmail())
				.phone(supplier.getPhone())
				.businessType(supplier.getBusinessType())
				.district(supplier.getDistrict())
				.city(supplier.getCity())
				.address(supplier.getAddress())
				.profileImage(supplier.getProfileImage())
				.verified(supplier.isVerified())
				.active(supplier.isActive())
				.createdAt(supplier.getCreatedAt())
				.updatedAt(supplier.getUpdatedAt())
				.build();
	}
}
