package com.percy.inventory.PurchaseRequest;

import com.percy.inventory.PurchaseRequest.dto.PurchaseRequestLineResponse;
import com.percy.inventory.PurchaseRequest.dto.PurchaseRequestResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseRequestMapper {

    public PurchaseRequestResponse toResponse(
            PurchaseRequest purchaseRequest
    ) {

        List<PurchaseRequestLineResponse> lines =
                purchaseRequest.getLines()
                        .stream()
                        .map(this::toLineResponse)
                        .toList();

        return new PurchaseRequestResponse(
                purchaseRequest.getId(),
                purchaseRequest.getRequestNo(),

                purchaseRequest.getRequester().getUserId(),
                purchaseRequest.getRequester().getUsername(),

                purchaseRequest.getRequestDate(),
                purchaseRequest.getRequiredDate(),
                purchaseRequest.getReason(),
                purchaseRequest.getNotes(),

                purchaseRequest.getWarehouse() != null
                        ? purchaseRequest.getWarehouse().getWarehouseId()
                        : null,

                purchaseRequest.getWarehouse() != null
                        ? purchaseRequest.getWarehouse().getName()
                        : null,

                purchaseRequest.getLocation() != null
                        ? purchaseRequest.getLocation().getLocationId()
                        : null,

                purchaseRequest.getLocation() != null
                        ? purchaseRequest.getLocation().getName()
                        : null,

                purchaseRequest.getStatus(),
                lines
        );
    }

    private PurchaseRequestLineResponse toLineResponse(
            PurchaseRequestLine line
    ) {

        return new PurchaseRequestLineResponse(
                line.getId(),

                line.getProduct() != null
                        ? line.getProduct().getProductId()
                        : null,

                line.getProduct() != null
                        ? line.getProduct().getProductName()
                        : null,

                line.getDescription(),
                line.getQuantity(),
                line.getUnit(),
                line.getRequiredDate(),
                line.getNotes()
        );
    }
}