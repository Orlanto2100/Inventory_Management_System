package com.percy.inventory.PurchaseOrder.dto;

import java.math.BigDecimal;

public record CreatePurchaseOrderLineRequest(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}