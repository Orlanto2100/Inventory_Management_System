package com.percy.inventory.PurchaseOrder;

import com.percy.inventory.PurchaseOrder.dto.CreatePurchaseOrderRequest;
import com.percy.inventory.PurchaseOrder.dto.CreatePurchaseOrderLineRequest;
import com.percy.inventory.PurchaseOrder.dto.PurchaseOrderLineResponse;
import com.percy.inventory.PurchaseOrder.dto.PurchaseOrderResponse;
import com.percy.inventory.PurchaseOrder.dto.UpdatePurchaseOrderLineRequest;
import com.percy.inventory.PurchaseOrder.dto.UpdatePurchaseOrderRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseOrderMapper {

    public PurchaseOrderResponse toResponse(PurchaseOrder purchaseOrder) {
        List<PurchaseOrderLineResponse> lines = purchaseOrder.getLines()
                .stream()
                .map(this::toLineResponse)
                .toList();

        return new PurchaseOrderResponse(
                purchaseOrder.getPurchaseOrderId(),
                purchaseOrder.getPurchaseOrderNumber(),
                purchaseOrder.getVendor().getVendorId(),
                purchaseOrder.getCreatedBy().getUserId(),
                purchaseOrder.getOrderDate(),
                purchaseOrder.getExpectedDeliveryDate(),
                purchaseOrder.getTotalAmount(),
                purchaseOrder.getNotes(),
                purchaseOrder.getStatus(),
                lines
        );
    }

    private PurchaseOrderLineResponse toLineResponse(
            PurchaseOrderLine line
    ) {
        return new PurchaseOrderLineResponse(
                line.getPurchaseOrderLinesId(),
                line.getProduct().getProductId(),
                line.getQuantity(),
                line.getUnitPrice()
        );
    }

    public PurchaseOrder toEntity(
            CreatePurchaseOrderRequest request
    ) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setOrderDate(request.orderDate());
        purchaseOrder.setExpectedDeliveryDate(request.expectedDeliveryDate());
        purchaseOrder.setNotes(request.notes());

        return purchaseOrder;
    }

    public PurchaseOrderLine toLineEntity(
            CreatePurchaseOrderLineRequest request
    ) {
        PurchaseOrderLine line = new PurchaseOrderLine();

        line.setQuantity(request.quantity());
        line.setUnitPrice(request.unitPrice());

        return line;
    }

    public void updateEntity(
            PurchaseOrder purchaseOrder,
            UpdatePurchaseOrderRequest request
    ) {
        purchaseOrder.setExpectedDeliveryDate(
                request.expectedDeliveryDate()
        );

        purchaseOrder.setNotes(request.notes());
    }

    public void updateLineEntity(
            PurchaseOrderLine line,
            UpdatePurchaseOrderLineRequest request
    ) {
        line.setQuantity(request.quantity());
        line.setUnitPrice(request.unitPrice());
    }
}