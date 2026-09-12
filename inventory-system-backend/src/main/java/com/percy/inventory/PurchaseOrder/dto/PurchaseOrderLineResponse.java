package com.percy.inventory.PurchaseOrder.dto;

import java.math.BigDecimal;

public record PurchaseOrderLineResponse(
        Long purchaseOrderLinesId,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}