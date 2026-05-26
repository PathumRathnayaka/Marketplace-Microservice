package com.cloudpos.supplier.controller;

import com.cloudpos.supplier.dto.ApiResponse;
import com.cloudpos.supplier.dto.OfferRequest;
import com.cloudpos.supplier.dto.OfferResponse;
import com.cloudpos.supplier.service.OfferService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

	private final OfferService offerService;

	@PostMapping
	public ResponseEntity<ApiResponse<OfferResponse>> create(@Valid @RequestBody OfferRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Offer created successfully", offerService.create(request)));
	}

	@GetMapping("/my-offers")
	public ResponseEntity<ApiResponse<List<OfferResponse>>> getMyOffers() {
		return ResponseEntity.ok(ApiResponse.success("Offers fetched successfully", offerService.getMyOffers()));
	}

	@PutMapping("/{id}/cancel")
	public ResponseEntity<ApiResponse<OfferResponse>> cancel(@PathVariable String id) {
		return ResponseEntity.ok(ApiResponse.success("Offer cancelled successfully", offerService.cancel(id)));
	}
}
