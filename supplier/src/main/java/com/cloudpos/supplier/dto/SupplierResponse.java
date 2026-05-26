package com.cloudpos.supplier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {

	private String id;
	private String businessName;
	private String ownerName;
	private String email;
	private String phone;
	private String businessType;
	private String district;
	private String city;
	private String address;
	private String profileImage;
	@JsonProperty("isVerified")
	private boolean verified;
	@JsonProperty("isActive")
	private boolean active;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
