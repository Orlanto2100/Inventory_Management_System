package com.percy.inventory.Rfqs.dto;

import java.math.BigDecimal;

public record RfqLineResponse(
        Long id,
        Long productId,
        String productName,
        BigDecimal quantity,
        String notes
) {
}