package com.percy.inventory.PurchaseOrder.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreatePurchaseOrderRequest(
        Long vendorId,
        LocalDateTime orderDate,
        LocalDateTime expectedDeliveryDate,
        String notes,
        List<CreatePurchaseOrderLineRequest> lines
) {
}