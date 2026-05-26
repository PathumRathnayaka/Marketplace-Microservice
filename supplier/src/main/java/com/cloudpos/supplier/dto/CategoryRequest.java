package com.cloudpos.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

	@NotBlank
	private String categoryName;
}
