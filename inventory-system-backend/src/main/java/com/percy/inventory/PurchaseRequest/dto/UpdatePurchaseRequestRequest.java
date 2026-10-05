package com.percy.inventory.PurchaseRequest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UpdatePurchaseRequestRequest(

        @NotNull
        @FutureOrPresent
        LocalDate requiredDate,

        @NotBlank
        String reason,

        String notes,

        @NotNull
        Long warehouseId,

        Long locationId,

        @NotEmpty
        @Valid
        List<PurchaseRequestLineRequest> lines
) {
}