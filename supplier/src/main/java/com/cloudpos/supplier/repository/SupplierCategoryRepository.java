package com.cloudpos.supplier.repository;

import com.cloudpos.supplier.entity.SupplierCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierCategoryRepository extends JpaRepository<SupplierCategory, String> {

	List<SupplierCategory> findBySupplierId(String supplierId);

	Optional<SupplierCategory> findByIdAndSupplierId(String id, String supplierId);
}
