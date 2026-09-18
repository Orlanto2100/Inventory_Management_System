package com.percy.inventory.Rfqs.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record CreateRfqRequest(

        @NotBlank
        @Size(max = 150)
        String title,

        @NotNull
        @Future
        LocalDateTime responseDeadline,

        @Size(max = 500)
        String description,

        @NotEmpty
        List<Long> vendorIds,

        @NotEmpty
        List<@Valid RfqLineRequest> items
) {
}