package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.DeliveryAreaRequest;
import com.cloudpos.supplier.dto.DeliveryAreaResponse;
import java.util.List;

public interface DeliveryAreaService {

	DeliveryAreaResponse create(DeliveryAreaRequest request);

	List<DeliveryAreaResponse> getMyDeliveryAreas();

	void delete(String id);
}
