package com.cloudpos.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenValidationRequestDTO {

    @NotBlank(message = "Access token is required")
    private String accessToken;
}
