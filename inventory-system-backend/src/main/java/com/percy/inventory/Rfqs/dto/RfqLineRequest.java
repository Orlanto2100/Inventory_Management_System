package com.percy.inventory.Rfqs.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RfqLineRequest(

        @NotNull
        Long productId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal quantity,

        @Size(max = 250)
        String notes
) {
}