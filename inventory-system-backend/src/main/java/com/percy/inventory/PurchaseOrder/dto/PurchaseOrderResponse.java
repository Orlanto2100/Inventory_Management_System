package com.percy.inventory.PurchaseOrder.dto;

import com.percy.inventory.PurchaseOrder.PurchaseOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PurchaseOrderResponse(
        Long purchaseOrderId,
        String purchaseOrderNumber,
        Long vendorId,
        Long createdById,
        LocalDateTime orderDate,
        LocalDateTime expectedDeliveryDate,
        BigDecimal totalAmount,
        String notes,
        PurchaseOrderStatus status,
        List<PurchaseOrderLineResponse> lines
) {
}