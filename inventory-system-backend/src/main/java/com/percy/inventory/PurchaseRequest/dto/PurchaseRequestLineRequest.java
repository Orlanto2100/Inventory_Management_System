package com.percy.inventory.PurchaseRequest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PurchaseRequestLineRequest(

        Long productId,

        String description,

        @NotNull
        @DecimalMin(value = "0.0001")
        BigDecimal quantity,

        @NotBlank
        String unit,

        LocalDate requiredDate,

        String notes
) {
}