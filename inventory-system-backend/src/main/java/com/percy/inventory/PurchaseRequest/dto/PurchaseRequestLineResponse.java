package com.percy.inventory.PurchaseRequest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PurchaseRequestLineResponse(

        Long id,

        Long productId,

        String productName,

        String description,

        BigDecimal quantity,

        String unit,

        LocalDate requiredDate,

        String notes
) {
}