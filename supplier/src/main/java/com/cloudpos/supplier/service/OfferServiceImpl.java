package com.cloudpos.supplier.service;

import com.cloudpos.supplier.dto.OfferRequest;
import com.cloudpos.supplier.dto.OfferResponse;
import com.cloudpos.supplier.entity.OfferStatus;
import com.cloudpos.supplier.entity.SupplierOffer;
import com.cloudpos.supplier.event.MarketplaceEventPublisher;
import com.cloudpos.supplier.event.SupplierOfferEventDTO;
import com.cloudpos.supplier.exception.BadRequestException;
import com.cloudpos.supplier.exception.ResourceNotFoundException;
import com.cloudpos.supplier.mapper.OfferMapper;
import com.cloudpos.supplier.repository.SupplierOfferRepository;
import com.cloudpos.supplier.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

	private final SupplierOfferRepository offerRepository;
	private final OfferMapper offerMapper;
	private final ObjectProvider<MarketplaceEventPublisher> eventPublisherProvider;

	@Override
	@Transactional
	public OfferResponse create(OfferRequest request) {
		SupplierOffer offer = SupplierOffer.builder()
				.supplierId(SecurityUtil.currentSupplierId())
				.lowStockRequestId(request.getLowStockRequestId())
				.shopTenantId(request.getShopTenantId())
				.productName(request.getProductName())
				.requestedQty(request.getRequestedQty())
				.offeredPrice(request.getOfferedPrice())
				.deliveryDays(request.getDeliveryDays())
				.note(request.getNote())
				.status(OfferStatus.PENDING)
				.build();
		SupplierOffer savedOffer = offerRepository.save(offer);
		eventPublisherProvider.ifAvailable(publisher ->
				publisher.publishSupplierOfferCreated(toEvent(savedOffer)));
		return offerMapper.toResponse(savedOffer);
	}

	@Override
	public List<OfferResponse> getMyOffers() {
		return offerRepository.findBySupplierId(SecurityUtil.currentSupplierId()).stream()
				.map(offerMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional
	public OfferResponse cancel(String id) {
		SupplierOffer offer = offerRepository.findByIdAndSupplierId(id, SecurityUtil.currentSupplierId())
				.orElseThrow(() -> new ResourceNotFoundException("Offer not found"));
		if (offer.getStatus() != OfferStatus.PENDING) {
			throw new BadRequestException("Only pending offers can be cancelled");
		}
		offer.setStatus(OfferStatus.CANCELLED);
		SupplierOffer savedOffer = offerRepository.save(offer);
		eventPublisherProvider.ifAvailable(publisher ->
				publisher.publishSupplierOfferCancelled(toEvent(savedOffer)));
		return offerMapper.toResponse(savedOffer);
	}

	private SupplierOfferEventDTO toEvent(SupplierOffer offer) {
		return SupplierOfferEventDTO.builder()
				.offerId(offer.getId())
				.supplierId(offer.getSupplierId())
				.lowStockRequestId(offer.getLowStockRequestId())
				.shopTenantId(offer.getShopTenantId())
				.productName(offer.getProductName())
				.requestedQty(offer.getRequestedQty())
				.offeredPrice(offer.getOfferedPrice())
				.deliveryDays(offer.getDeliveryDays())
				.status(offer.getStatus())
				.occurredAt(LocalDateTime.now())
				.build();
	}
}
