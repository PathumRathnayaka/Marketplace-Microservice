package com.cloudpos.supplier.repository;

import com.cloudpos.supplier.entity.DeliveryArea;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAreaRepository extends JpaRepository<DeliveryArea, String> {

	List<DeliveryArea> findBySupplierId(String supplierId);

	Optional<DeliveryArea> findByIdAndSupplierId(String id, String supplierId);
}
