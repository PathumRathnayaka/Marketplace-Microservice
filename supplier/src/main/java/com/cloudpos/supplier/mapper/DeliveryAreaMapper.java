package com.cloudpos.supplier.mapper;

import com.cloudpos.supplier.dto.DeliveryAreaResponse;
import com.cloudpos.supplier.entity.DeliveryArea;
import org.springframework.stereotype.Component;

@Component
public class DeliveryAreaMapper {

	public DeliveryAreaResponse toResponse(DeliveryArea area) {
		return DeliveryAreaResponse.builder()
				.id(area.getId())
				.supplierId(area.getSupplierId())
				.district(area.getDistrict())
				.city(area.getCity())
				.deliveryFee(area.getDeliveryFee())
				.estimatedDays(area.getEstimatedDays())
				.createdAt(area.getCreatedAt())
				.build();
	}
}
