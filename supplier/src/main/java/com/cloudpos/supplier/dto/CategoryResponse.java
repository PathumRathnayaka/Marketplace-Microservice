package com.cloudpos.supplier.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

	private String id;
	private String supplierId;
	private String categoryName;
	private LocalDateTime createdAt;
}
