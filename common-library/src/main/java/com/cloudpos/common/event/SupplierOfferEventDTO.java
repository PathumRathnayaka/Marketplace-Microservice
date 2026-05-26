package com.cloudpos.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOfferEventDTO {

    private String offerId;
    private String supplierId;
    private String supplierName;
    private String requestId;
    private BigDecimal offeredPrice;
    private Integer deliveryDays;
    private LocalDateTime createdAt;
}
