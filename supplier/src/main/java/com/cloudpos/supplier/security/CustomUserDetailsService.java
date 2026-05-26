package com.cloudpos.supplier.security;

import com.cloudpos.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final SupplierRepository supplierRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return supplierRepository.findByEmailAndDeletedFalse(username)
				.map(SupplierPrincipal::from)
				.orElseThrow(() -> new UsernameNotFoundException("Supplier not found"));
	}
}
