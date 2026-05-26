package com.cloudpos.supplier.auth;

import com.cloudpos.supplier.dto.AuthResponse;
import com.cloudpos.supplier.dto.LoginRequest;
import com.cloudpos.supplier.dto.RegisterRequest;
import com.cloudpos.supplier.dto.SupplierResponse;
import com.cloudpos.supplier.entity.Supplier;
import com.cloudpos.supplier.exception.DuplicateResourceException;
import com.cloudpos.supplier.exception.ResourceNotFoundException;
import com.cloudpos.supplier.mapper.SupplierMapper;
import com.cloudpos.supplier.repository.SupplierRepository;
import com.cloudpos.supplier.security.JwtTokenProvider;
import com.cloudpos.supplier.security.SupplierPrincipal;
import com.cloudpos.supplier.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final SupplierRepository supplierRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final SupplierMapper supplierMapper;

	@Override
	@Transactional
	public AuthResponse register(RegisterRequest request) {
		String email = request.getEmail().trim().toLowerCase();
		if (supplierRepository.existsByEmail(email)) {
			throw new DuplicateResourceException("Email is already registered");
		}

		Supplier supplier = Supplier.builder()
				.businessName(request.getBusinessName())
				.ownerName(request.getOwnerName())
				.email(email)
				.phone(request.getPhone())
				.password(passwordEncoder.encode(request.getPassword()))
				.businessType(request.getBusinessType())
				.district(request.getDistrict())
				.city(request.getCity())
				.address(request.getAddress())
				.profileImage(request.getProfileImage())
				.verified(false)
				.active(true)
				.deleted(false)
				.build();
		Supplier savedSupplier = supplierRepository.save(supplier);
		String token = jwtTokenProvider.generateToken(SupplierPrincipal.from(savedSupplier));
		return AuthResponse.builder()
				.accessToken(token)
				.supplier(supplierMapper.toResponse(savedSupplier))
				.build();
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword()));
		SupplierPrincipal principal = (SupplierPrincipal) authentication.getPrincipal();
		Supplier supplier = supplierRepository.findByIdAndDeletedFalse(principal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
		String token = jwtTokenProvider.generateToken(principal);
		return AuthResponse.builder()
				.accessToken(token)
				.supplier(supplierMapper.toResponse(supplier))
				.build();
	}

	@Override
	public SupplierResponse me() {
		String supplierId = SecurityUtil.currentSupplierId();
		Supplier supplier = supplierRepository.findByIdAndDeletedFalse(supplierId)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
		return supplierMapper.toResponse(supplier);
	}
}
