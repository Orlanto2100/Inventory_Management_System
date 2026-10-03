package com.percy.inventory.PurchaseRequest.dto;

import com.percy.inventory.PurchaseRequest.PurchaseRequestStatus;

import java.time.LocalDate;
import java.util.List;

public record PurchaseRequestResponse(

        Long id,

        String requestNo,

        Long requesterId,

        String requesterName,

        String department,

        LocalDate requestDate,

        LocalDate requiredDate,

        String reason,

        String notes,

        Long warehouseId,

        String warehouseName,

        Long locationId,

        String locationName,

        PurchaseRequestStatus status,

        List<PurchaseRequestLineResponse> lines
) {
}