package com.cloudpos.supplier.repository;

import com.cloudpos.supplier.entity.SupplierOffer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierOfferRepository extends JpaRepository<SupplierOffer, String> {

	List<SupplierOffer> findBySupplierId(String supplierId);

	Optional<SupplierOffer> findByIdAndSupplierId(String id, String supplierId);
}
