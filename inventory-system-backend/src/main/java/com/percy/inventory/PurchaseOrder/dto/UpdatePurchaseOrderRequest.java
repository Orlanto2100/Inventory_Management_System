package com.percy.inventory.PurchaseOrder.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UpdatePurchaseOrderRequest(
        LocalDateTime expectedDeliveryDate,
        String notes,
        List<UpdatePurchaseOrderLineRequest> lines
) {
}