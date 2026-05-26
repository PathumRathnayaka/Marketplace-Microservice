package com.cloudpos.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	@NotBlank
	private String businessName;

	@NotBlank
	private String ownerName;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String phone;

	@NotBlank
	@Size(min = 8, max = 100)
	private String password;

	@NotBlank
	private String businessType;

	@NotBlank
	private String district;

	@NotBlank
	private String city;

	@NotBlank
	private String address;

	private String profileImage;
}
