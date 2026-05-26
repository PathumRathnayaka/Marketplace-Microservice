package com.cloudpos.supplier.auth;

import com.cloudpos.supplier.dto.AuthResponse;
import com.cloudpos.supplier.dto.LoginRequest;
import com.cloudpos.supplier.dto.RegisterRequest;
import com.cloudpos.supplier.dto.SupplierResponse;

public interface AuthService {

	AuthResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

	SupplierResponse me();
}
