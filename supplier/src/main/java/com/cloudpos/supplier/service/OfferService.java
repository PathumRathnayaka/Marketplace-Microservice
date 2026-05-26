package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.OfferRequest;
import com.cloudpos.supplier.dto.OfferResponse;
import java.util.List;

public interface OfferService {

	OfferResponse create(OfferRequest request);

	List<OfferResponse> getMyOffers();

	OfferResponse cancel(String id);
}
