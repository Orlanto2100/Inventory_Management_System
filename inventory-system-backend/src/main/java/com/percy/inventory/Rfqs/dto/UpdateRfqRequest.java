package com.percy.inventory.Rfqs.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateRfqRequest(

        @Size(max = 150)
        String title,

        @Future
        LocalDateTime responseDeadline,

        @Size(max = 500)
        String description,

        List<Long> vendorIds,

        List<@Valid RfqLineRequest> items
) {
}