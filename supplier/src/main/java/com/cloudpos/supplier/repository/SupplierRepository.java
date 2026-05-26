package com.cloudpos.supplier.repository;

import com.cloudpos.supplier.entity.Supplier;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, String> {

	Optional<Supplier> findByEmail(String email);

	Optional<Supplier> findByIdAndDeletedFalse(String id);

	Optional<Supplier> findByEmailAndDeletedFalse(String email);

	boolean existsByEmail(String email);
}
