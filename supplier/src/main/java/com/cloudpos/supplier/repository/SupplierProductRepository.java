package com.cloudpos.supplier.repository;

import com.cloudpos.supplier.entity.SupplierProduct;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, String> {

	List<SupplierProduct> findBySupplierIdAndDeletedFalse(String supplierId);

	List<SupplierProduct> findByDeletedFalseAndAvailableTrue();

	Optional<SupplierProduct> findByIdAndSupplierIdAndDeletedFalse(String id, String supplierId);
}
