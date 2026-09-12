package com.percy.inventory.PurchaseOrder.dto;

import java.math.BigDecimal;

public record UpdatePurchaseOrderLineRequest(
        Long purchaseOrderLineId,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}