package com.percy.inventory.Rfqs.dto;

import com.percy.inventory.Rfqs.RfqStatus;

import java.time.LocalDateTime;
import java.util.List;

public record RfqResponse(
        Long id,
        String rfqNumber,
        String title,
        LocalDateTime requestDate,
        LocalDateTime responseDeadline,
        String description,
        RfqStatus status,
        List<Long> vendorIds,
        int vendorCount,
        int itemCount,
        List<RfqLineResponse> items
) {
}