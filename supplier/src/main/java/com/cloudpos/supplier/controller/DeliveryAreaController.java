package com.cloudpos.supplier.controller;

import com.cloudpos.supplier.dto.ApiResponse;
import com.cloudpos.supplier.dto.DeliveryAreaRequest;
import com.cloudpos.supplier.dto.DeliveryAreaResponse;
import com.cloudpos.supplier.service.DeliveryAreaService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery-areas")
@RequiredArgsConstructor
public class DeliveryAreaController {

	private final DeliveryAreaService deliveryAreaService;

	@PostMapping
	public ResponseEntity<ApiResponse<DeliveryAreaResponse>> create(@Valid @RequestBody DeliveryAreaRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Delivery area created successfully", deliveryAreaService.create(request)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<DeliveryAreaResponse>>> getMyDeliveryAreas() {
		return ResponseEntity.ok(ApiResponse.success("Delivery areas fetched successfully",
				deliveryAreaService.getMyDeliveryAreas()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
		deliveryAreaService.delete(id);
		return ResponseEntity.ok(ApiResponse.success("Delivery area deleted successfully", null));
	}
}
