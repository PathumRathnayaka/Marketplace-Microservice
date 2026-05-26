package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.DeliveryAreaRequest;
import com.cloudpos.supplier.dto.DeliveryAreaResponse;
import com.cloudpos.supplier.entity.DeliveryArea;
import com.cloudpos.supplier.exception.ResourceNotFoundException;
import com.cloudpos.supplier.mapper.DeliveryAreaMapper;
import com.cloudpos.supplier.repository.DeliveryAreaRepository;
import com.cloudpos.supplier.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryAreaServiceImpl implements DeliveryAreaService {

	private final DeliveryAreaRepository deliveryAreaRepository;
	private final DeliveryAreaMapper deliveryAreaMapper;

	@Override
	@Transactional
	public DeliveryAreaResponse create(DeliveryAreaRequest request) {
		DeliveryArea area = DeliveryArea.builder()
				.supplierId(SecurityUtil.currentSupplierId())
				.district(request.getDistrict())
				.city(request.getCity())
				.deliveryFee(request.getDeliveryFee())
				.estimatedDays(request.getEstimatedDays())
				.build();
		return deliveryAreaMapper.toResponse(deliveryAreaRepository.save(area));
	}

	@Override
	public List<DeliveryAreaResponse> getMyDeliveryAreas() {
		return deliveryAreaRepository.findBySupplierId(SecurityUtil.currentSupplierId()).stream()
				.map(deliveryAreaMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional
	public void delete(String id) {
		DeliveryArea area = deliveryAreaRepository.findByIdAndSupplierId(id, SecurityUtil.currentSupplierId())
				.orElseThrow(() -> new ResourceNotFoundException("Delivery area not found"));
		deliveryAreaRepository.delete(area);
	}
}
