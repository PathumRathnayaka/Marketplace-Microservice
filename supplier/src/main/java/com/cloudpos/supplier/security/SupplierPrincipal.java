package com.cloudpos.supplier.security;

import com.cloudpos.supplier.entity.Supplier;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class SupplierPrincipal implements UserDetails {

	private final String id;
	private final String email;
	private final String password;
	private final boolean active;

	public SupplierPrincipal(String id, String email, String password, boolean active) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.active = active;
	}

	public static SupplierPrincipal from(Supplier supplier) {
		return new SupplierPrincipal(
				supplier.getId(),
				supplier.getEmail(),
				supplier.getPassword(),
				supplier.isActive() && !supplier.isDeleted());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_SUPPLIER"));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
}
