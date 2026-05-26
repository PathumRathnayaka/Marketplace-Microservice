package com.cloudpos.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LowStockRequestEventDTO {

    private String requestId;
    private String tenantId;
    private String shopName;
    private String productId;
    private String productName;
    private String category;
    private Integer requiredQuantity;
    private LocalDateTime createdAt;
}
